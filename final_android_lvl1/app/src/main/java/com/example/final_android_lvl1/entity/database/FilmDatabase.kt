package com.example.final_android_lvl1.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.final_android_lvl1.data.retrofit.dto.preview.CountryDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.GenreDto
import com.google.gson.annotations.SerializedName

@Entity(tableName = "filmInfo")
data class FilmDatabase(
    @PrimaryKey
    @ColumnInfo(name = "id_film_and_collection")
    val filmAndCollectionId: Int,
    @ColumnInfo(name = "id_collection")
    val idCollection: Int,
    @ColumnInfo(name = "id_film")
    val kinopoiskId: Int,
    @ColumnInfo(name = "film_id")
    val filmId: Int,
    @ColumnInfo(name = "name_en")
    val nameEn: String,
    @ColumnInfo(name = "name_original")
    val nameOriginal: String,
    @ColumnInfo(name = "name_ru")
    val nameRu: String,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String,
    @ColumnInfo(name = "poster_url_preview")
    val posterUrlPreview: String,
    @ColumnInfo(name = "premiere_ru")
    val premiereRu: String,
    @ColumnInfo(name = "rating_kinopoisk")
    val ratingKinopoisk: Double,
    @ColumnInfo(name = "rating")
    val rating: String,
    @ColumnInfo(name = "countries")
    val countries: String?,                  //  List<Country>
    @ColumnInfo(name = "genres")
    val genres: String?,         //  List<Genre>
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "type")
    val type: String


)
