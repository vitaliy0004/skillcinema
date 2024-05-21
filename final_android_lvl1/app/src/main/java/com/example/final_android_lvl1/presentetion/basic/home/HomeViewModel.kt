package com.example.final_android_lvl1.presentetion.basic.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.final_android_lvl1.data.retrofit.dto.filter_country_genre.CountryAndGenreDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.FilmDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.domain.Repository
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.home.all_collection.AllCollectionPagingSource
import com.example.final_android_lvl1.presentetion.basic.search.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    val listCountry = mutableMapOf<Int, String>()
    val listGenre = mutableMapOf<Int, String>()
    val randomCountry = mutableListOf<Int>()
    val randomGenre = mutableListOf<Int>()
    var countrys = mutableListOf<Int>(0, 0)
    var genre = mutableListOf<Int>(0, 0)

    val month = mapOf<Int, String>(
        1 to "JANUARY",
        2 to "FEBRUARY",
        3 to "MARCH",
        4 to "APRIL",
        5 to "MAY",
        6 to "JUNE",
        7 to "JULY",
        8 to "AUGUST",
        9 to "SEPTEMBER",
        10 to "OCTOBER",
        11 to "NOVEMBER",
        12 to "DECEMBER"
    )
    val collectionName =
        mutableListOf<String>("Премьера", "Популярные", "Топ 250 фильмов", "", "", "ТВ Сериалы")


    lateinit var pagedMovies: Flow<PagingData<ListFilmDto>>
    fun pagedMovies(viewModel: MainViewModel, context: Context) {
        pagedMovies = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { AllCollectionPagingSource(_state, viewModel, this, context) }
        ).flow.cachedIn(viewModelScope)
    }


    lateinit var listFilm: FilmDto


    fun filterCountryAndGenre(countryAndGenre: CountryAndGenreDto) {
        countryAndGenre.countries.forEach {
            listCountry[it.id] = it.country
            randomCountry.add(it.id)
        }
        countryAndGenre.genres.forEach {
            listGenre[it.id] = it.genre
            randomGenre.add(it.id)
        }
    }

    suspend fun CountryAndGenre(): CountryAndGenreDto {
        return repository.filterCountryAndGenre()
    }

    suspend fun getPremiers(year: Int, month: String, page: Int) {
        if (page == 1) listFilm = repository.premiers(year, month, page)
        else listFilm.items = emptyList()
    }

    suspend fun getPopular(page: Int) {
        listFilm = repository.popular(page)
    }

    suspend fun getTopFilm(page: Int) {
        listFilm = repository.topFilm(page)
    }

    suspend fun getMiniSeries(miniSeriesString: String, page: Int) {
        listFilm = repository.miniSeries(miniSeriesString, page)
    }

    suspend fun getCountryAndGenre(country: Int, genre: Int, page: Int) {
        listFilm = repository.countryAndGenre(country, genre, page)
    }

    suspend fun countryAndGenre(count: Int, page: Int) {
        getCountryAndGenre(
            randomCountry(count),
            randomGenre(count),
            page
        )
        while (listFilm.items.size < 10) getCountryAndGenre(
            randomCountry(count),
            randomGenre(count),
            page
        )
    }


    fun randomCountry(count: Int): Int {
        if (count == 0) countrys[0] = randomCountry.random()
        else countrys[1] = randomCountry.random()
        return countrys[count]
    }

    fun randomGenre(count: Int): Int {
        if (count == 0) genre[0] = randomGenre.random()
        else genre[1] = randomGenre.random()
        return genre[count]
    }

    private fun cupName(nameFilms: String): String {
        return if (nameFilms.length > 20) nameFilms.filterIndexed { index, c -> index < 17 } + "..."
        else nameFilms
    }

    fun nameCollectionCountryAndGenre(count: Int): String {
        val nameCountry = listCountry[countrys[count]]
        val nameGenre = listGenre[genre[count]]
        collectionName[count + 3] = cupName("$nameCountry $nameGenre")
        return collectionName[count + 3]
    }

    fun nameCollection(collection: String): String {
        return if (collection.length > 20) collection.filterIndexed { index, c -> index < 17 } + "..."
        else collection
    }

    suspend fun twoWeeks(viewModel: MainViewModel) {
        var listFilmDto = mutableListOf<ListFilmDto>()
        var timeRealise = Calendar.getInstance()
        val timeNow = Calendar.getInstance()
        timeNow.set(Calendar.MONTH, timeNow.get(Calendar.MONTH))
        listFilm.items.forEach {
            timeRealise = viewModel.data(it.premiereRu)
            if (timeRealise.timeInMillis > timeNow.timeInMillis && timeRealise.timeInMillis < timeNow.timeInMillis + (1000 * 60 * 60 * 24 * 14)) {
                viewModel.premieresFilm.add(it.kinopoiskId)
                listFilmDto.add(it)
            }
        }
        timeNow.timeInMillis += 1000 * 60 * 60 * 24 * 14
        if (timeNow.get(Calendar.DAY_OF_MONTH) > 14) listFilm.items = listFilmDto
        else {
            getPremiers(timeNow.get(Calendar.YEAR), month[timeNow.get(Calendar.MONTH) + 1]!!, 1)
            listFilm.items.forEach {
                timeRealise.timeInMillis = viewModel.data(it.premiereRu).timeInMillis
                if (timeNow.timeInMillis >= timeRealise.timeInMillis) {
                    listFilmDto.add(it)
                }
            }
            listFilm.items = listFilmDto
        }
    }


    suspend fun controllerCollection(
        paging: Int,
        controller: Int,
        country1: Int,
        country2: Int,
        genre1: Int,
        genre2: Int,
        viewModel: MainViewModel
    ): FilmDto {
        when (controller) {
            1 -> {
                getPremiers(
                    Calendar.getInstance().get(Calendar.YEAR),
                    month[Calendar.getInstance().get(Calendar.MONTH) + 1]!!, paging
                )
                twoWeeks(viewModel)
            }

            2 -> getPopular(paging)

            3 -> getTopFilm(paging)

            4 -> getCountryAndGenre(country1, genre1, paging)

            5 -> getCountryAndGenre(country2, genre2, paging)

            else -> getMiniSeries("TV_SERIES", paging)
        }
        return listFilm
    }


}
