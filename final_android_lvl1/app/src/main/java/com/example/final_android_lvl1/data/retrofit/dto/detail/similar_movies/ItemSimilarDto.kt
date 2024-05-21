package com.example.final_android_lvl1.data.retrofit.dto.detail.similar_movies

import com.example.final_android_lvl1.entity.retrofit.detal.similar_movies.ItemSimilar
import com.google.gson.annotations.SerializedName

data class ItemSimilarDto(
    @SerializedName("filmId") override val filmId: Int,
    @SerializedName("nameEn") override val nameEn: String,
    @SerializedName("nameOriginal") override val nameOriginal: String,
    @SerializedName("nameRu") override val nameRu: String,
    @SerializedName("posterUrl") override val posterUrl: String,
    @SerializedName("posterUrlPreview") override val posterUrlPreview: String,
    @SerializedName("relationType") override val relationType: String
) : ItemSimilar