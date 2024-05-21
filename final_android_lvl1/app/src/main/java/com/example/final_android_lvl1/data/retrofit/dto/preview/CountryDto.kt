package com.example.final_android_lvl1.data.retrofit.dto.preview

import com.example.final_android_lvl1.entity.retrofit.preview.Country
import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("country") override val country: String
) : Country