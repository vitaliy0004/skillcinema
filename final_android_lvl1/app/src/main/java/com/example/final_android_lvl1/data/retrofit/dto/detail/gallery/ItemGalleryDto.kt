package com.example.final_android_lvl1.data.retrofit.dto.detail.gallery

import com.example.final_android_lvl1.entity.retrofit.detal.gallery.ItemGallery
import com.google.gson.annotations.SerializedName

data class ItemGalleryDto(
    @SerializedName("imageUrl") override val imageUrl: String,
    @SerializedName("previewUrl") override val previewUrl: String
) : ItemGallery
