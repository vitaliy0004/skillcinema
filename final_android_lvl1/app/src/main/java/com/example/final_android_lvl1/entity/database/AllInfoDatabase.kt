package com.example.final_android_lvl1.entity.database

import androidx.room.Embedded
import androidx.room.Relation

data class AllInfoDatabase(
    @Embedded
    val collectionFilm: CollectionFilmDatabase,
    @Relation(
        entity = FilmDatabase::class,
        parentColumn = "id_collection",
        entityColumn = "id_collection"
    )
    val listFilm: List<FilmDatabase>?
)