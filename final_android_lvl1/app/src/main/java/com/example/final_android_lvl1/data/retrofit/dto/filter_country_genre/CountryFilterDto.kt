package com.example.final_android_lvl1.data.retrofit.dto.filter_country_genre

import com.example.final_android_lvl1.entity.retrofit.filter_country_genre.CountryFilter
import com.google.gson.annotations.SerializedName

data class CountryFilterDto(
    @SerializedName("country") override val country: String,
    @SerializedName("id") override val id: Int
) : CountryFilter