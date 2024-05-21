package com.example.final_android_lvl1.data.retrofit.dto.detail.person

import com.example.final_android_lvl1.entity.retrofit.detal.person.Films
import com.google.gson.annotations.SerializedName

data class FilmsDto(
    @SerializedName("description") override val description: String,
    @SerializedName("filmId") override val filmId: Int,
    @SerializedName("general") override val general: Boolean,
    @SerializedName("nameEn") override val nameEn: String,
    @SerializedName("nameRu") override val nameRu: String,
    @SerializedName("professionKey") override val professionKey: String,
    @SerializedName("rating") override val rating: String
) : Films