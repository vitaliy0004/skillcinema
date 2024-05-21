package com.example.final_android_lvl1.presentetion.basic.search.filter_genre_country

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.final_android_lvl1.data.retrofit.dto.filter_country_genre.CountryAndGenreDto
import com.example.final_android_lvl1.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddParamsFilterViewModule @Inject constructor(private val repository: Repository) :
    ViewModel() {
    val listCountry = mutableListOf<String>()
    val listGenre = mutableListOf<String>()
    val mapCountry = mutableMapOf<String, Int>()
    val mapGenre = mutableMapOf<String, Int>()
    val filter = mutableListOf<String>()
    fun sortedList(countryAndGenre: CountryAndGenreDto) {
        countryAndGenre.countries.forEach {
            listCountry.add(it.country)
            mapCountry[it.country] = it.id
        }
        countryAndGenre.genres.forEach {
            listGenre.add(it.genre)
            mapGenre[it.genre] = it.id
        }
    }

    suspend fun countryAndGenre(): CountryAndGenreDto {
        return repository.filterCountryAndGenre()
    }

    fun adapter(context: Context, int: Int, list: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(context, int, list)
    }

    fun filterParams(world: String, list: List<String>): List<String> {
        filter.clear()
        list.forEach {
            val counterText = world.length
            if (world == it.filterIndexed { index, c -> index < counterText })
                filter.add(it)
        }
        return filter
    }
}