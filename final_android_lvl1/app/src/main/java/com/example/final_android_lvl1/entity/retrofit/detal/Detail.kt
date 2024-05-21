package com.example.final_android_lvl1.entity.retrofit.detal

import com.example.final_android_lvl1.data.retrofit.dto.preview.CountryDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.GenreDto

interface Detail {
    val countries: List<CountryDto>
    val description: String
    val editorAnnotation: Any
    val filmLength: Int
    val genres: List<GenreDto>
    val imdbId: String
    val kinopoiskId: Int
    val lastSync: String
    val nameEn: Any
    val nameOriginal: String
    val nameRu: String
    val posterUrl: String
    val posterUrlPreview: String
    val ratingAgeLimits: String
    val ratingImdb: Double
    val ratingKinopoisk: Double
    val serial: Boolean
    val shortFilm: Boolean
    val slogan: String
    val type: String
    val year: Int
}