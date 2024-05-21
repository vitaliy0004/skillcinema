package com.example.final_android_lvl1.entity.retrofit.detal.gallery

import com.example.final_android_lvl1.data.retrofit.dto.detail.gallery.ItemGalleryDto

interface Gallery {
    val items: List<ItemGalleryDto>
    val total: Int
    val totalPages: Int
}