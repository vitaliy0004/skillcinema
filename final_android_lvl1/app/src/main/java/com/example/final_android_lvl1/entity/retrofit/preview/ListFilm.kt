package com.example.final_android_lvl1.entity.retrofit.preview

import com.example.final_android_lvl1.data.retrofit.dto.preview.CountryDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.GenreDto

interface ListFilm {
    val countries: List<CountryDto>
    val genres: List<GenreDto>
    val filmId: Int
    val premiereRu:String
    val kinopoiskId: Int
    val nameEn: Any
    val nameOriginal: String
    val nameRu: String
    val posterUrl: String
    val posterUrlPreview: String
    val ratingKinopoisk: Double
     val rating: String
    //val ratingImdb: Double
    val type: String
    val year: Int
}