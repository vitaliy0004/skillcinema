package com.example.final_android_lvl1.entity.retrofit.detal.similar_movies

import com.example.final_android_lvl1.data.retrofit.dto.detail.similar_movies.ItemSimilarDto

interface SimilarMovies {
    val items: List<ItemSimilarDto>
    val total: Int
}