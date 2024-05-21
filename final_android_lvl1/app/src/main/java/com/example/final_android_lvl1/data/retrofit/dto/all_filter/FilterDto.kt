package com.example.final_android_lvl1.data.retrofit.dto.all_filter

import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.entity.retrofit.all_filter.Filter
import com.google.gson.annotations.SerializedName

data class FilterDto(
    @SerializedName("items")override val items: List<ListFilmDto>,
    @SerializedName("total")override val total: Int,
    @SerializedName("totalPages")override val totalPages: Int
): Filter