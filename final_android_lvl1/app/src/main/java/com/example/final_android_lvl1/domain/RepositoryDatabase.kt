package com.example.final_android_lvl1.domain

import android.util.Log
import com.example.final_android_lvl1.data.databas.FilmDao
import com.example.final_android_lvl1.data.retrofit.dto.preview.CountryDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.GenreDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.entity.database.AllInfoDatabase
import com.example.final_android_lvl1.entity.database.CollectionFilmDatabase
import com.example.final_android_lvl1.entity.database.FilmDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RepositoryDatabase @Inject constructor(
    private val filmDao: FilmDao
) {
    fun allCollectionFilm(): Flow<List<AllInfoDatabase>> {
        return filmDao.getAllCollection()
    }

    suspend fun getCollection(idCollection: Int): List<ListFilmDto> {
        val listFilm = mutableListOf<ListFilmDto>()
        val listDatabase = filmDao.getCollection(idCollection)
        if (listDatabase.isNotEmpty()) {
            listDatabase.forEach {
                listFilm.add(formatListFilm(it))
            }
        }
        return listFilm
    }

    suspend fun addCollection(Collection: CollectionFilmDatabase) {
        filmDao.addCollection(Collection)
    }

    suspend fun addFilm(film: ListFilmDto, idCollection: Int) {
        filmDao.addFilm(formatFilm(film, idCollection))
    }

    suspend fun filmDelete(idFilm: Int) {
        filmDao.filmDelete(idFilm)
    }

    suspend fun collectionDelete(idCollection: Int) {
        filmDao.collectionDelete(idCollection)
        filmDao.deleteCollectionFilm(idCollection)
    }

    suspend fun collectionFilmDelete(idCollection: Int) {
        filmDao.deleteCollectionFilm(idCollection)
    }

    private fun formatFilm(film: ListFilmDto, collection: Int): FilmDatabase {
        val filmId = if (film.kinopoiskId == null || film.kinopoiskId == 0) film.filmId
        else film.kinopoiskId
        val idFilmAndCollection = collection * 100_000_000 + filmId
        val rating = if (film.rating == null) film.ratingKinopoisk.toString()
        else film.rating
        return FilmDatabase(
            kinopoiskId = filmId,
            filmId = filmId,
            nameEn = "",
            nameOriginal = if (film.nameOriginal == null) film.nameRu
            else film.nameOriginal,
            nameRu = if (film.nameRu == null) film.nameOriginal
            else film.nameRu,
            posterUrl = film.posterUrl,
            posterUrlPreview = film.posterUrlPreview,
            premiereRu = if (film.premiereRu == null) ""
            else film.premiereRu,
            ratingKinopoisk = 0.0,
            rating = rating,
            countries = if (film.countries == null || film.countries.isEmpty()) ""
            else film.countries[0].country,
            genres = if (film.genres == null || film.genres.isEmpty()) ""
            else film.genres[0].genre,
            idCollection = collection,
            filmAndCollectionId = idFilmAndCollection,
            year = film.year,
            type = film.type
        )
    }

    private fun formatListFilm(film: FilmDatabase): ListFilmDto {
        val listCountry = mutableListOf<CountryDto>()
        val listGenre = mutableListOf<GenreDto>()
        if (film.countries != null) listCountry.add(CountryDto(film.countries))
        if (film.genres != null) listGenre.add(GenreDto(film.genres))

        return ListFilmDto(
            countries = listCountry,
            genres = listGenre,
            kinopoiskId = film.kinopoiskId,
            filmId = film.filmId,
            posterUrl = film.posterUrl,
            posterUrlPreview = film.posterUrlPreview,
            nameEn = film.nameEn,
            nameOriginal = film.nameOriginal,
            nameRu = film.nameRu,
            premiereRu = film.premiereRu,
            ratingKinopoisk = 0.0,
            rating = film.rating,
            type = film.type,
            year = film.year

        )
    }

}