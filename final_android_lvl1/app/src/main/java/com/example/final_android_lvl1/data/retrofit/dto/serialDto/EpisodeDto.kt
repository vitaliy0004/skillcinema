package com.example.final_android_lvl1.data.retrofit.dto.serialDto

import com.example.final_android_lvl1.entity.retrofit.detal.serial.Episode
import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    @SerializedName("episodeNumber") override val episodeNumber: Int,
    @SerializedName("nameEn") override val nameEn: Any,
    @SerializedName("nameRu") override val nameRu: String,
    @SerializedName("releaseDate") override val releaseDate: String,
    @SerializedName("seasonNumber") override val seasonNumber: Int,
    @SerializedName("synopsis") override val synopsis: Any
) : Episode