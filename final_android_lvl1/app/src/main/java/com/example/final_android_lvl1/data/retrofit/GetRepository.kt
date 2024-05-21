package com.example.final_android_lvl1.data.retrofit

import com.example.final_android_lvl1.data.retrofit.dto.all_filter.FilterDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.AllFilmParticipantsDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.DetailDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.gallery.GalleryDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.person.PersonDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.similar_movies.SimilarMoviesDto
import com.example.final_android_lvl1.data.retrofit.dto.filter_country_genre.CountryAndGenreDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.FilmDto
import com.example.final_android_lvl1.data.retrofit.dto.serialDto.SerialDto
import javax.inject.Inject

class GetRepository @Inject constructor(private val retrofitFilm: RetrofitFilm) {
    suspend fun getPremiers(year: Int, month: String, page: Int): FilmDto {
        return retrofitFilm.getFilm.premiers(year, month, page)
    }

    suspend fun getPopular(page: Int): FilmDto {
        return retrofitFilm.getFilm.popular("NUM_VOTE", page)
    }

    suspend fun getTopFilm(page: Int): FilmDto {
        return retrofitFilm.getFilm.topFilm(page)
    }

    suspend fun getMiniSeries(miniSeries: String, page: Int): FilmDto {
        return retrofitFilm.getFilm.miniSeries(miniSeries, page)
    }

    suspend fun getCountryAndGenre(country: Int, genre: Int, page: Int): FilmDto {
        return retrofitFilm.getFilm.countryAndGenre(country, genre, page)
    }

    suspend fun getFilterCountryAndGenre(): CountryAndGenreDto {
        return retrofitFilm.getFilm.filterCountryAndGenre()
    }

    suspend fun getDetail(id: Int): DetailDto {
        return retrofitFilm.getFilm.detail(id)
    }

    suspend fun getEpisode(id: Int): SerialDto {
        return retrofitFilm.getFilm.episode(id)
    }

    suspend fun getAllFilmParticipants(filmId: Int): AllFilmParticipantsDto {
        return retrofitFilm.getFilm.allFilmParticipants(filmId)
    }

    suspend fun getGallery(id: Int, page: Int, type: String): GalleryDto {
        return retrofitFilm.getFilm.gallery(id, page, type)
    }

    suspend fun getSimilarMovies(id: Int): SimilarMoviesDto {
        return retrofitFilm.getFilm.similarMovies(id)
    }

    suspend fun getPerson(id: Int): PersonDto {
        return retrofitFilm.getFilm.person(id)
    }

    suspend fun getFilter(
        mapString: Map<String, String>,
        mapInt: Map<String, Int>,
        page: Int

    ): FilterDto {
        return retrofitFilm.getFilm.filter(
            mapString,
            mapInt,
            page
        )
    }
}