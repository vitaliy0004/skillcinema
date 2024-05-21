package com.example.final_android_lvl1.data.retrofit.dto.preview

import com.example.final_android_lvl1.entity.retrofit.preview.Film
import com.google.gson.annotations.SerializedName

data class FilmDto(
    @SerializedName("items") override var items: List<ListFilmDto>,
    @SerializedName("films") override val films: List<ListFilmDto>,
    // @SerializedName("total") override val total: Int,
    @SerializedName("totalPages") override val totalPages: Int
) : Film