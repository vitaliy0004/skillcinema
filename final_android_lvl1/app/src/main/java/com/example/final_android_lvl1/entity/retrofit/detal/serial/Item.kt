package com.example.final_android_lvl1.entity.retrofit.detal.serial

import com.example.final_android_lvl1.data.retrofit.dto.serialDto.EpisodeDto

interface Item {
    val episodes: List<EpisodeDto>
    val number: Int
}