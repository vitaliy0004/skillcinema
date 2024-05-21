package com.example.final_android_lvl1.data.databas

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.final_android_lvl1.entity.database.CollectionFilmDatabase
import com.example.final_android_lvl1.entity.database.FilmDatabase

@Database(
    entities = [
        CollectionFilmDatabase::class,
        FilmDatabase::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class Database : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}