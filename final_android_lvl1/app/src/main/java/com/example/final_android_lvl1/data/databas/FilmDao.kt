package com.example.final_android_lvl1.data.databas

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.final_android_lvl1.entity.database.AllInfoDatabase
import com.example.final_android_lvl1.entity.database.CollectionFilmDatabase
import com.example.final_android_lvl1.entity.database.FilmDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM collectionFilm")
    fun getAllCollection(): Flow<List<AllInfoDatabase>>

    @Query("SELECT * FROM filmInfo WHERE id_collection LIKE :collection ")
    suspend fun getCollection(collection: Int): List<FilmDatabase>

    @Query("DELETE FROM collectionFilm WHERE id_collection LIKE :collection")
    suspend fun collectionDelete(collection: Int)

    @Query("DELETE FROM filmInfo WHERE id_collection LIKE :collection")
    suspend fun deleteCollectionFilm(collection: Int)

    @Query("DELETE FROM  filmInfo WHERE id_film_and_collection LIKE :idFilm")
    suspend fun filmDelete(idFilm: Int)

    @Insert(entity = FilmDatabase::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilm(film: FilmDatabase)

    @Insert
    suspend fun addCollection(collectionInfo: CollectionFilmDatabase)
}