package com.example.final_android_lvl1.entity.retrofit.all_filter

import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto

interface Filter {
    val items: List<ListFilmDto>
    val total: Int
    val totalPages: Int
}