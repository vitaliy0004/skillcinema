package com.example.final_android_lvl1.presentetion.basic

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.data.retrofit.dto.detail.AllFilmParticipantsDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.DetailDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.FilmParticipantsDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.gallery.ItemGalleryDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.person.PersonDto
import com.example.final_android_lvl1.data.retrofit.dto.filter_country_genre.CountryAndGenreDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.FilmDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.data.retrofit.dto.serialDto.SerialDto
import com.example.final_android_lvl1.databinding.MainItemCollectionPersonFilmBinding
import com.example.final_android_lvl1.databinding.MainItemDetailBinding
import com.example.final_android_lvl1.databinding.MainItemPreviewFilmItemBinding
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule
import kotlinx.coroutines.launch
import java.util.Calendar

class MainViewModel : ViewModel() {
    var isBack = false
    lateinit var navController: NavController
    var bottomNavigation = mutableMapOf<Int, MutableList<Int>>(
        1 to mutableListOf(R.id.homeFragments),
        2 to mutableListOf(R.id.searchFragments),
        3 to mutableListOf(R.id.profileFragments)
    )
    var counterBottomNavigation = 1

    var controllerCollection = 0
    var countrys = mutableListOf<Int>(0, 0)
    var genres = mutableListOf<Int>(0, 0)

    val premieresFilm = mutableSetOf<Int>()

    //  проверка повторных запросов
    val previewCollection = mutableMapOf<Int, FilmDto>()
    val listDetailFilm = mutableMapOf<Int, DetailDto>()
    val listAllFilmParticipants = mutableMapOf<Int, AllFilmParticipantsDto?>()
    val listEpisode = mutableMapOf<Int, SerialDto?>()
    val listGallery = mutableMapOf<Int, List<ItemGalleryDto>>()
    val listSimilarMovies = mutableMapOf<Int, List<ListFilmDto>>()
    val listPerson = mutableMapOf<Int, PersonDto>()

    // для сахранения филтра
    var filterMapString = mutableMapOf<String, String?>(
        "imdbId" to null,
        "keyword" to null,
        "type" to "ALL",
        "order" to "YEAR",
    )
    var filterMapInt = mutableMapOf<String, Int?>(
        "countries" to null,
        "genres" to null,
        "ratingFrom" to 1,
        "ratingTo" to 10,
        "yearFrom" to 1000,
        "yearTo" to 3000,
    )
    var isCountry = true
    val filterCountryAndGenre = mutableListOf<String>("", "")
    var isViewedFilm = false

    // для перехода с окна деталей на колекцию
    var listFilm = mutableMapOf<Int, MutableList<List<ListFilmDto>>>(
        1 to mutableListOf(listOf()), 2 to mutableListOf(listOf()), 3 to mutableListOf(listOf())
    )
    var listWorkerAndActor = mutableMapOf<Int, MutableList<MutableList<FilmParticipantsDto>>>(
        1 to mutableListOf(mutableListOf()),
        2 to mutableListOf(mutableListOf()),
        3 to mutableListOf(mutableListOf())
    )


    //для нахождения нужного фото в списке
    var photoPosition = mutableMapOf<Int, Int>(1 to 0, 2 to 0, 3 to 0)
    var listPhotoGallery = mutableMapOf<Int, List<ItemGalleryDto>>(
        1 to listOf<ItemGalleryDto>(), 2 to listOf<ItemGalleryDto>(), 3 to listOf<ItemGalleryDto>()
    )

    var nameCollection = mutableMapOf<Int, MutableList<String>>(
        1 to mutableListOf(),
        2 to mutableListOf(),
        3 to mutableListOf()
    )
    var idFilm = mutableMapOf<Int, MutableList<Int>>(
        1 to mutableListOf(),
        2 to mutableListOf(),
        3 to mutableListOf()
    )
    var idPerson = mutableMapOf<Int, MutableList<Int>>(
        1 to mutableListOf(),
        2 to mutableListOf(),
        3 to mutableListOf()
    )
    var countryAndGenre: CountryAndGenreDto? = null


    fun photoDetail(position: Int, navController: Unit, listPhoto: List<ItemGalleryDto>) {
        listPhotoGallery[counterBottomNavigation] = listPhoto
        photoPosition[counterBottomNavigation] = position
        navController
    }

    fun data(data: String): Calendar {
        var timeMillis = Calendar.getInstance()
        var counter = 0
        var stringData = ""
        data.forEach {
            counter += 1
            when (counter) {
                in 1..3 -> stringData += it
                4 -> {
                    stringData += it
                    timeMillis.set(Calendar.YEAR, stringData.toInt())
                    stringData = ""
                }

                6 -> stringData += it
                7 -> {
                    stringData += it
                    timeMillis.set(Calendar.MONTH, stringData.toInt() - 1)
                    stringData = ""
                }

                9 -> stringData += it
                10 -> {
                    stringData += it
                    timeMillis.set(Calendar.DAY_OF_MONTH, stringData.toInt())

                    stringData = ""
                }

            }
        }
        return timeMillis
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun cupName(nameFilms: String): String {
        return if (nameFilms.length > 20) nameFilms.filterIndexed { index, c -> index < 17 } + "..."
        else nameFilms
    }

    @SuppressLint("SuspiciousIndentation")
    fun imageFilm(
        profileViewModule: ProfileViewModule,
        binding: MainItemPreviewFilmItemBinding,
        listFilmDto: ListFilmDto,
        onClickFilm: (Int) -> Unit,
        onClickViewed: (ListFilmDto) -> Unit,
        onClickNotViewed: (ListFilmDto) -> Unit
    ) {
        viewModelScope.launch {
            binding.genre.text = if (listFilmDto.genres.isEmpty()) ""
            else listFilmDto.genres[0].genre
            val nameFilms = if (listFilmDto.nameRu == null) listFilmDto.nameOriginal
            else listFilmDto.nameRu
            val idFilmDto = if (listFilmDto.kinopoiskId == 0) listFilmDto.filmId
            else listFilmDto.kinopoiskId
            binding.nameFilm.text = cupName(nameFilms)
            listFilmDto.let {
                Glide
                    .with(binding.imageView.context)
                    .load(it.posterUrlPreview)
                    .into(binding.imageView)
            }
            Log.d("main:","${listFilmDto.premiereRu},")
            if (listFilmDto.premiereRu != null && listFilmDto.premiereRu != "" && data(listFilmDto.premiereRu).timeInMillis > Calendar.getInstance().timeInMillis) {

                premieresFilm.add(listFilmDto.kinopoiskId)
                binding.view.visibility = View.INVISIBLE
                binding.viewOf.visibility = View.INVISIBLE
            } else {
                var isViewedFilm = false
                profileViewModule.saveCollection(1).forEach {
                    if (it.kinopoiskId == idFilmDto) isViewedFilm = true
                }
                if (!isViewedFilm) {
                    binding.view.visibility = View.INVISIBLE
                    binding.viewOf.visibility = View.VISIBLE
                } else {
                    binding.view.visibility = View.VISIBLE
                    binding.viewOf.visibility = View.INVISIBLE
                }
            }
            binding.view.setOnClickListener {
                onClickNotViewed(listFilmDto)
                binding.view.visibility = View.INVISIBLE
                binding.viewOf.visibility = View.VISIBLE
            }
            binding.viewOf.setOnClickListener {
                onClickViewed(listFilmDto)
                binding.view.visibility = View.VISIBLE
                binding.viewOf.visibility = View.INVISIBLE
            }

            if (listFilmDto.ratingKinopoisk != 0.0 && listFilmDto.rating == null) binding.itemFilmRating.text =
                listFilmDto.ratingKinopoisk.toString()
            else if (
                listFilmDto.ratingKinopoisk == 0.0
                && listFilmDto.rating != null
                && listFilmDto.rating != "0.0"
                && listFilmDto.rating != ""
            ) binding.itemFilmRating.text = listFilmDto.rating
            else binding.itemFilmRating.visibility = View.INVISIBLE

            binding.imageView.setOnClickListener {
                onClickFilm(
                    idFilmDto
                )
            }
        }
    }


    fun workerFilms(
        binding: MainItemCollectionPersonFilmBinding,
        listFilmDto: ListFilmDto,
        onClickFilm: (Int) -> Unit,
    ) {

        binding.genre.text = if (listFilmDto.genres.isEmpty()) ""
        else listFilmDto.genres[0].genre
        val nameFilms = if (listFilmDto.nameRu == null) listFilmDto.nameOriginal
        else listFilmDto.nameRu
        binding.nameFilm.text = cupName(nameFilms)
        listFilmDto.let {
            Glide
                .with(binding.imageView.context)
                .load(it.posterUrlPreview)
                .into(binding.imageView)
        }
        if (listFilmDto.ratingKinopoisk != 0.0 && listFilmDto.rating == null) binding.itemFilmRating.text =
            listFilmDto.ratingKinopoisk.toString()
        else if (listFilmDto.ratingKinopoisk == 0.0 && listFilmDto.rating != null && listFilmDto.rating != "0.0") binding.itemFilmRating.text =
            listFilmDto.rating
        else binding.itemFilmRating.visibility = View.INVISIBLE

        binding.imageView.setOnClickListener {
            onClickFilm(
                if (listFilmDto.kinopoiskId == 0) listFilmDto.filmId
                else listFilmDto.kinopoiskId
            )
        }
    }

    fun formatListFilm(detailFilm: DetailDto): ListFilmDto {
        return ListFilmDto(
            genres = detailFilm.genres,
            countries = detailFilm.countries,
            ratingKinopoisk = 0.0,
            posterUrlPreview = detailFilm.posterUrlPreview,
            posterUrl = detailFilm.posterUrl,
            premiereRu = detailFilm.lastSync,
            nameEn = "",
            nameOriginal = if (detailFilm.nameOriginal == null) detailFilm.nameRu
            else detailFilm.nameOriginal,
            nameRu = if (detailFilm.nameRu == null) detailFilm.nameOriginal
            else detailFilm.nameRu,
            kinopoiskId = detailFilm.kinopoiskId,
            filmId = detailFilm.kinopoiskId,
            rating = detailFilm.ratingKinopoisk.toString(),
            year = detailFilm.year,
            type = detailFilm.type
        )

    }

    fun isBookmark(
        detailInform: MainItemDetailBinding,
        profileViewModule: ProfileViewModule,
        film: ListFilmDto
    ) {
        viewModelScope.launch {
            var isFavoriteFilm = false
            val idFilm = if (film.kinopoiskId == 0) film.filmId
            else film.kinopoiskId
            profileViewModule.saveCollection(4).forEach {
                if (it.kinopoiskId == idFilm) isFavoriteFilm = true
            }
            if (!isFavoriteFilm) {
                detailInform.bookmark.visibility = View.GONE
                detailInform.bookmarkOf.visibility = View.VISIBLE
            } else {
                detailInform.bookmark.visibility = View.VISIBLE
                detailInform.bookmarkOf.visibility = View.GONE
            }
        }
    }

    fun isFavorite(
        detailInform: MainItemDetailBinding,
        profileViewModule: ProfileViewModule,
        film: ListFilmDto
    ) {
        viewModelScope.launch {
            var isFavoriteFilm = false
            val idFilm = if (film.kinopoiskId == 0) film.filmId
            else film.kinopoiskId
            profileViewModule.saveCollection(3).forEach {
                if (it.kinopoiskId == idFilm) isFavoriteFilm = true
            }
            if (!isFavoriteFilm) {
                detailInform.favorite.visibility = View.GONE
                detailInform.noFavorite.visibility = View.VISIBLE
            } else {
                detailInform.favorite.visibility = View.VISIBLE
                detailInform.noFavorite.visibility = View.GONE
            }
        }

    }

    fun backArrow(deleteId: Int, context: FragmentActivity) {
        isBack = true
        when (deleteId) {
            R.id.detailFilmFragment -> idFilm[counterBottomNavigation]!!.removeLast()
            R.id.personFragment -> idPerson[counterBottomNavigation]!!.removeLast()
            R.id.collectionFilm -> {
                nameCollection[counterBottomNavigation]!!.removeLast()
                listFilm[counterBottomNavigation]!!.removeLast()
            }

            R.id.allPersonFragment -> listWorkerAndActor[counterBottomNavigation]!!.removeLast()
            R.id.searchFragments -> context.finish()
            R.id.profileFragments -> context.finish()
            R.id.homeFragments -> context.finish()
        }

        bottomNavigation[counterBottomNavigation]!!.removeLast()
        if (bottomNavigation[counterBottomNavigation]!!.isNotEmpty())
            navController.navigate(bottomNavigation[counterBottomNavigation]!!.last())
    }

    fun backPressed(context: FragmentActivity, lifecycleOwner: LifecycleOwner, deleteId: Int) {
        context
            .onBackPressedDispatcher
            .addCallback(lifecycleOwner) {
                backArrow(deleteId, context)
            }
    }
}