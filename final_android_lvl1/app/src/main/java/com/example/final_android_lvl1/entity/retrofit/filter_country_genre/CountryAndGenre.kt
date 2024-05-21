package com.example.final_android_lvl1.entity.retrofit.filter_country_genre

import com.example.final_android_lvl1.data.retrofit.dto.filter_country_genre.CountryFilterDto
import com.example.final_android_lvl1.data.retrofit.dto.filter_country_genre.GenreFilterDto

interface CountryAndGenre {
    val countries: List<CountryFilterDto>
    val genres: List<GenreFilterDto>
}