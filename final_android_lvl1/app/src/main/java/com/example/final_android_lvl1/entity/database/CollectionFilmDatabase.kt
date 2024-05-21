package com.example.final_android_lvl1.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collectionFilm")
data class CollectionFilmDatabase(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_collection")
    val idCollection: Int? = null,
    @ColumnInfo(name = "name_Collection")
    val nameCollection: String
)