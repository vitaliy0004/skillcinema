package com.example.final_android_lvl1.entity.retrofit.detal.serial

import com.example.final_android_lvl1.data.retrofit.dto.serialDto.ItemDto

interface Serial {
    val items: List<ItemDto>
    val total: Int
}