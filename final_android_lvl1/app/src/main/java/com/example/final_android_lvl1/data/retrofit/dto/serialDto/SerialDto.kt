package com.example.final_android_lvl1.data.retrofit.dto.serialDto

import com.example.final_android_lvl1.entity.retrofit.detal.serial.Serial
import com.google.gson.annotations.SerializedName

data class SerialDto(
    @SerializedName("items") override val items: List<ItemDto>,
    @SerializedName("total") override val total: Int
) : Serial