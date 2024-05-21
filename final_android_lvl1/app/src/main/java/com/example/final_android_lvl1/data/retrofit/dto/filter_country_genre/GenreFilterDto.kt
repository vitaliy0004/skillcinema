package com.example.final_android_lvl1.data.retrofit.dto.filter_country_genre

import com.example.final_android_lvl1.entity.retrofit.filter_country_genre.GenreFilter
import com.google.gson.annotations.SerializedName

data class GenreFilterDto(
    @SerializedName("genre") override val genre: String,
    @SerializedName("id") override val id: Int
) : GenreFilter