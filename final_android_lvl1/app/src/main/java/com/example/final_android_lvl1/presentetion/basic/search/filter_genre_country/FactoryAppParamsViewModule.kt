package com.example.final_android_lvl1.presentetion.basic.search.filter_genre_country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_android_lvl1.domain.Repository
import javax.inject.Inject

class FactoryAppParamsViewModule @Inject constructor(private val films: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddParamsFilterViewModule(films) as T
    }
}