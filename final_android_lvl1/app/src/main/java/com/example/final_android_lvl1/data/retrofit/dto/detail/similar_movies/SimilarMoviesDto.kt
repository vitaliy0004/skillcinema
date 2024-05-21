package com.example.final_android_lvl1.data.retrofit.dto.detail.similar_movies

import com.example.final_android_lvl1.entity.retrofit.detal.similar_movies.SimilarMovies
import com.google.gson.annotations.SerializedName

data class SimilarMoviesDto(
    @SerializedName("items") override val items: List<ItemSimilarDto>,
    @SerializedName("total") override val total: Int
) : SimilarMovies
