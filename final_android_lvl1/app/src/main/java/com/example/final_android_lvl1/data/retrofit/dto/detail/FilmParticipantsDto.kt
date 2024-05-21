package com.example.final_android_lvl1.data.retrofit.dto.detail

import com.example.final_android_lvl1.entity.retrofit.detal.FilmParticipants
import com.google.gson.annotations.SerializedName

data class FilmParticipantsDto(
    @SerializedName("description") override val description: String,
    @SerializedName("nameEn") override val nameEn: String,
    @SerializedName("nameRu") override val nameRu: String,
    @SerializedName("posterUrl") override val posterUrl: String,
    @SerializedName("professionKey") override val professionKey: String,
    @SerializedName("professionText") override val professionText: String,
    @SerializedName("staffId") override val staffId: Int
) : FilmParticipants