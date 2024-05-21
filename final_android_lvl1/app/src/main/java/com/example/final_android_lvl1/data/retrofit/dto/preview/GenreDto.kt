package com.example.final_android_lvl1.data.retrofit.dto.preview

import com.example.final_android_lvl1.entity.retrofit.preview.Genre
import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("genre") override val genre: String
) : Genre