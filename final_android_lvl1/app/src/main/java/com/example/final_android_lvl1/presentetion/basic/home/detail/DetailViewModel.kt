package com.example.final_android_lvl1.presentetion.basic.home.detail

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.final_android_lvl1.data.retrofit.dto.detail.AllFilmParticipantsDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.DetailDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.FilmParticipantsDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.gallery.ItemGalleryDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.CountryDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.data.retrofit.dto.serialDto.SerialDto
import com.example.final_android_lvl1.databinding.MainItemDetailBinding
import com.example.final_android_lvl1.databinding.MainItemFilmWasFilmedBinding
import com.example.final_android_lvl1.domain.Repository
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var listActor = mutableListOf<FilmParticipantsDto>()
    var listNOActor = mutableListOf<FilmParticipantsDto>()


    fun sortActorAndWorker(allParticipants: AllFilmParticipantsDto?) {
        if (allParticipants != null) {
            allParticipants.forEach {
                if (it.professionKey == "ACTOR" || it.professionText == "Актеры") listActor.add(it)
                else listNOActor.add(it)
            }
        }
    }

    suspend fun allFilmParticipants(filmId: Int): AllFilmParticipantsDto {
        return repository.allFilmParticipants(filmId)
    }

    suspend fun detail(id: Int): DetailDto {
        return repository.detail(id)
    }

    suspend fun episode(id: Int): SerialDto {
        return repository.episode(id)
    }

    suspend fun similarMovies(id: Int, viewModel: MainViewModel): List<ListFilmDto> {
        val listDetail = mutableListOf<ListFilmDto>()
        repository.similarMovies(id).items.forEach {
            val detailFilm = if (viewModel.listDetailFilm[it.filmId] == null) detail(it.filmId)
            else viewModel.listDetailFilm[it.filmId]!!
            listDetail.add(
                ListFilmDto(
                    genres = detailFilm.genres,
                    premiereRu = detailFilm.lastSync,
                    ratingKinopoisk = 0.0,
                    posterUrlPreview = detailFilm.posterUrlPreview,
                    posterUrl = detailFilm.posterUrl,
                    countries = detailFilm.countries,
                    nameEn = "",
                    nameOriginal = if (detailFilm.nameOriginal == null) detailFilm.nameRu
                    else detailFilm.nameOriginal,
                    nameRu = if (detailFilm.nameRu == null) detailFilm.nameOriginal
                    else detailFilm.nameRu,
                    kinopoiskId = detailFilm.kinopoiskId,
                    filmId = detailFilm.kinopoiskId,
                    rating = detailFilm.ratingKinopoisk.toString(),
                    type = detailFilm.type,
                    year = detailFilm.year
                )
            )
        }
        return listDetail
    }

    suspend fun gallery(id: Int, type: String): List<ItemGalleryDto> {
        val galleryOne = repository.gallery(id, 1, type)
        var counter = 1
        val itemGalleryDto = mutableListOf<ItemGalleryDto>()
        galleryOne.items.forEach {
            itemGalleryDto.add(it)
        }
        while (counter <= galleryOne.totalPages) {
            counter++
            repository.gallery(id, counter, type).items.forEach {
                itemGalleryDto.add(it)
            }
        }
        return itemGalleryDto
    }

    fun viewPeople(binding: MainItemFilmWasFilmedBinding, listSize: Int) {
        if (listSize > 20) binding.layoutTableOfContents.visibility = View.VISIBLE
        else binding.layoutTableOfContents.visibility = View.INVISIBLE
    }

    fun ageLimits(ratingAgeLimits: String?): String {
        return if (ratingAgeLimits == null) "0+"
        else ratingAgeLimits.filterIndexed { index, c -> index > 2 } + "+"
    }

    fun timeFilm(allMinute: Int): String {
        var hour = 0
        var minute = 0
        return if (allMinute > 59) {
            hour = allMinute / 60
            minute = allMinute - hour * 60
            if (minute == 0) "$hour ч,"
            else "$hour ч $minute мин,"
        } else if (allMinute == 0) ""
        else {
            minute = allMinute
            "$minute мин,"
        }
    }

    fun description(description: String?): String {
        return if (description == null) ""
        else if (description.length > 250) description.filterIndexed { index, c -> index <= 247 } + "..."
        else description

    }

    fun filmOrSerial(detailName: DetailDto, season: SerialDto?): String {
        return if (season == null)
            if (detailName.genres == null || detailName.genres.isEmpty()) ""
                else detailName.genres[0].genre
        else "${detailName.genres[0].genre},${season.items[0].number} сезон"
    }

    fun episodeText(seasonDto: SerialDto?): String {
        return if (seasonDto == null) ""
        else {
            if (seasonDto.items.isEmpty()) ""
            val season = seasonDto.items[0].number
            val episodes = seasonDto.items[0].episodes.size
            "$season cезон, $episodes серий"
        }
    }

    fun filmName(nameOriginal: String?, nameRu: String?): String {
        return if (nameRu == null) nameOriginal!!
        else nameRu
    }

    fun country(countries: List<CountryDto>): String {
        return if (countries.isEmpty()) "no Country"
        else countries[0].country
    }

    fun rating(ratingKinopoisk: Double?): String {
        return if (ratingKinopoisk == 0.0 && ratingKinopoisk == null) ""
        else "$ratingKinopoisk,"
    }

    suspend fun viewFilm(
        binding: MainItemDetailBinding,
        profileViewModule: ProfileViewModule,
        listFilm: ListFilmDto
    ) {
        var isViewedFilm = false
        val idFilmDto = if (listFilm.kinopoiskId == 0) listFilm.filmId
        else listFilm.kinopoiskId
        profileViewModule.saveCollection(1).forEach {
            if (it.kinopoiskId == idFilmDto) isViewedFilm = true
        }
        if (!isViewedFilm) {
            binding.view.visibility = View.GONE
            binding.viewOf.visibility = View.VISIBLE
        } else {
            binding.view.visibility = View.VISIBLE
            binding.viewOf.visibility = View.GONE
        }
    }

    fun premieresFilm(premieresFilm: MutableSet<Int>, id: Int): Boolean {
        var controller = false
        premieresFilm.forEach {
            if (it == id) controller = true
        }
        return controller
    }

    suspend fun season(serial: Boolean, id: Int, binding: MainItemDetailBinding): SerialDto? {
        return if (serial) {
            val episodes = episode(id)
            if (episodes.items.isEmpty()) {
                binding.episode.episodeLayoutId.visibility = View.GONE
                null
            } else {
                binding.episode.episodeLayoutId.visibility = View.VISIBLE
                episodes
            }
        } else {
            binding.episode.episodeLayoutId.visibility = View.GONE
            null
        }
    }

    fun repeatSeason(season: SerialDto?, binding: MainItemDetailBinding): SerialDto? {
        if (season == null) binding.episode.episodeLayoutId.visibility = View.GONE
        else binding.episode.episodeLayoutId.visibility = View.VISIBLE
        return season
    }
}