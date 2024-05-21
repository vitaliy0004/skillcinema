package com.example.final_android_lvl1.entity.retrofit.preview

import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto

interface Film {
    val items: List<ListFilmDto>
    val films: List<ListFilmDto>
    //val total: Int
    val totalPages: Int
}