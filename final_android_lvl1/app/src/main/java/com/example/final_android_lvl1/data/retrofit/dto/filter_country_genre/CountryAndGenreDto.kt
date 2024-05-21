package com.example.final_android_lvl1.data.retrofit.dto.filter_country_genre

import com.example.final_android_lvl1.entity.retrofit.filter_country_genre.CountryAndGenre
import com.google.gson.annotations.SerializedName

data class CountryAndGenreDto(
    @SerializedName("countries") override val countries: List<CountryFilterDto>,
    @SerializedName("genres") override val genres: List<GenreFilterDto>
) : CountryAndGenre