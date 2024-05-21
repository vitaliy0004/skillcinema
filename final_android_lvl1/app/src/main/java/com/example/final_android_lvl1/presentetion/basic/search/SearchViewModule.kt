package com.example.final_android_lvl1.presentetion.basic.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.final_android_lvl1.data.retrofit.dto.all_filter.FilterDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.domain.Repository
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModule @Inject constructor(private val repository: Repository) : ViewModel() {
    lateinit var context: Context
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()
    lateinit var pagedFilter: Flow<PagingData<ListFilmDto>>
    fun pagedFilter(viewModule: MainViewModel) {
        pagedFilter = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CollectionFilmPagingSource(_state, viewModule, this) }
        ).flow.cachedIn(viewModelScope)

    }

    private suspend fun filter(
        mapString: Map<String, String>,
        mapInt: Map<String, Int>,
        page: Int
    ): FilterDto {
        return repository.filter(
            mapString, mapInt, page
        )
    }

    suspend fun listFilmFilter(viewModule: MainViewModel, page: Int): FilterDto? {
        return if (!viewModule.isOnline(context)) {
            throw Exception("")
            null
        } else {
            val mapString = mutableMapOf<String, String>()
            val mapInt = mutableMapOf<String, Int>()
            viewModule.filterMapInt.forEach { s, i ->
                if (i != null) mapInt[s] = i
            }
            viewModule.filterMapString.forEach { s, i ->
                if (i != null) mapString[s] = i
            }
            filter(mapString, mapInt, page)
        }
    }
}