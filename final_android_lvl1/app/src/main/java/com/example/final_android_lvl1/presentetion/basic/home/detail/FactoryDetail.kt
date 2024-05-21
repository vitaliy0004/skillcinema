package com.example.final_android_lvl1.presentetion.basic.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_android_lvl1.domain.Repository
import javax.inject.Inject

class FactoryDetail @Inject constructor(private val films: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(films) as T
    }
}