package com.example.final_android_lvl1.domain

import com.example.final_android_lvl1.data.retrofit.GetRepository
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

class Repository @Inject constructor(
    private val getRepository: GetRepository
) {

    suspend fun premiers(year: Int, month: String, page: Int): FilmDto {
        return getRepository.getPremiers(year, month, page)
    }

    suspend fun popular(page: Int): FilmDto {
        return getRepository.getPopular(page)
    }

    suspend fun topFilm(page: Int): FilmDto {
        return getRepository.getTopFilm(page)
    }

    suspend fun miniSeries(miniSeries: String, page: Int): FilmDto {
        return getRepository.getMiniSeries(miniSeries, page)
    }

    suspend fun countryAndGenre(country: Int, genre: Int, page: Int): FilmDto {
        return getRepository.getCountryAndGenre(country, genre, page)
    }

    suspend fun filterCountryAndGenre(): CountryAndGenreDto {
        return getRepository.getFilterCountryAndGenre()
    }

    suspend fun detail(id: Int): DetailDto {
        return getRepository.getDetail(id)
    }

    suspend fun episode(id: Int): SerialDto {
        return getRepository.getEpisode(id)
    }

    suspend fun allFilmParticipants(filmId: Int): AllFilmParticipantsDto {
        return getRepository.getAllFilmParticipants(filmId)
    }

    suspend fun gallery(id: Int, page: Int, type: String): GalleryDto {
        return getRepository.getGallery(id, page, type)
    }

    suspend fun similarMovies(id: Int): SimilarMoviesDto {
        return getRepository.getSimilarMovies(id)
    }

    suspend fun person(id: Int): PersonDto {
        return getRepository.getPerson(id)
    }

    suspend fun filter(
        mapString: Map<String, String>,
        mapInt: Map<String, Int>,
        page: Int
    ): FilterDto {
        return getRepository.getFilter(
            mapString, mapInt, page
        )
    }

}