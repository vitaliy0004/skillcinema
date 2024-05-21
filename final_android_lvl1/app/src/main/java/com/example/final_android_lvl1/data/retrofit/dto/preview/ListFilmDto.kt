package com.example.final_android_lvl1.data.retrofit.dto.preview

import com.example.final_android_lvl1.entity.retrofit.preview.ListFilm
import com.google.gson.annotations.SerializedName

data class ListFilmDto(
    @SerializedName("countries") override val countries: List<CountryDto>,
    @SerializedName("genres") override val genres: List<GenreDto>,
    @SerializedName("kinopoiskId") override val kinopoiskId: Int,
    @SerializedName("filmId") override val filmId: Int,
    @SerializedName("nameEn") override val nameEn: String,
    @SerializedName("nameOriginal") override val nameOriginal: String,
    @SerializedName("nameRu") override val nameRu: String,
    @SerializedName("posterUrl") override val posterUrl: String,
    @SerializedName("posterUrlPreview") override val posterUrlPreview: String,
    @SerializedName("premiereRu") override val premiereRu: String,
    @SerializedName("ratingKinopoisk") override val ratingKinopoisk: Double,
    @SerializedName("rating") override val rating: String,
    // @SerializedName("ratingImdb") override val ratingImdb: Double,
    @SerializedName("type") override val type: String,
    @SerializedName("year") override val year: Int
) : ListFilm