package com.example.final_android_lvl1.di

import android.content.Context
import androidx.room.Room
import com.example.final_android_lvl1.data.databas.FilmDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseDi {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): com.example.final_android_lvl1.data.databas.Database {
        return Room.databaseBuilder(
            context.applicationContext,
            com.example.final_android_lvl1.data.databas.Database::class.java,
            "bd"
        ).build()
    }

    @Provides
    fun provideDao(appDataBase: com.example.final_android_lvl1.data.databas.Database): FilmDao {
        return appDataBase.filmDao()
    }
}