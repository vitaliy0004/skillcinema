package com.example.final_android_lvl1.data.retrofit.dto.detail.gallery

import com.example.final_android_lvl1.entity.retrofit.detal.gallery.Gallery
import com.google.gson.annotations.SerializedName

data class GalleryDto(
    @SerializedName("items") override val items: List<ItemGalleryDto>,
    @SerializedName("total") override val total: Int,
    @SerializedName("totalPages") override val totalPages: Int
) : Gallery