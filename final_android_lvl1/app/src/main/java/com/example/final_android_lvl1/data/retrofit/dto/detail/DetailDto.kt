package com.example.final_android_lvl1.data.retrofit.dto.detail

import com.example.final_android_lvl1.data.retrofit.dto.preview.CountryDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.GenreDto
import com.example.final_android_lvl1.entity.retrofit.detal.Detail
import com.google.gson.annotations.SerializedName

data class DetailDto(

    @SerializedName("countries") override val countries: List<CountryDto>,
    @SerializedName("description") override val description: String,
    @SerializedName("editorAnnotation") override val editorAnnotation: Any,
    @SerializedName("filmLength") override val filmLength: Int,
    @SerializedName("genres") override val genres: List<GenreDto>,
    @SerializedName("imdbId") override val imdbId: String,
    @SerializedName("kinopoiskId") override val kinopoiskId: Int,
    @SerializedName("lastSync") override val lastSync: String,
    @SerializedName("nameEn") override val nameEn: Any,
    @SerializedName("nameOriginal") override val nameOriginal: String,
    @SerializedName("nameRu") override val nameRu: String,
    @SerializedName("posterUrl") override val posterUrl: String,
    @SerializedName("posterUrlPreview") override val posterUrlPreview: String,
    @SerializedName("ratingAgeLimits") override val ratingAgeLimits: String,
    @SerializedName("ratingImdb") override val ratingImdb: Double,
    @SerializedName("ratingKinopoisk") override val ratingKinopoisk: Double,
    @SerializedName("serial") override val serial: Boolean,
    @SerializedName("shortFilm") override val shortFilm: Boolean,
    @SerializedName("slogan") override val slogan: String,
    @SerializedName("type") override val type: String,
    @SerializedName("year") override val year: Int

    /*

    val completed: Boolean,
    val coverUrl: String,
    val endYear: Any,
    val has3D: Boolean,
    val hasImax: Boolean,
    val isTicketsAvailable: Boolean,
    val kinopoiskHDId: String,
    val logoUrl: String,
    val productionStatus: Any,
    val ratingAwait: Any,
    val ratingAwaitCount: Int,
    val ratingFilmCritics: Double,
    val ratingFilmCriticsVoteCount: Int,
    val ratingGoodReview: Int,
    val ratingGoodReviewVoteCount: Int,
    val ratingImdbVoteCount: Int,
    val ratingKinopoiskVoteCount: Int,
    val ratingMpaa: String,
    val startYear: Any,
    val webUrl: String,*/
) : Detail