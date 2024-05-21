package com.example.final_android_lvl1.data.retrofit.dto.serialDto

import com.example.final_android_lvl1.entity.retrofit.detal.serial.Item
import com.google.gson.annotations.SerializedName

data class ItemDto(
    @SerializedName("episodes") override val episodes: List<EpisodeDto>,
    @SerializedName("number") override val number: Int
) : Item