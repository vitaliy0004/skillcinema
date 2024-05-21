package com.example.final_android_lvl1.presentetion.basic.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.presentetion.ConstantString
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class CollectionFilmPagingSource(
    private val state: MutableStateFlow<State>,
    private val viewModel: MainViewModel,
    private val searchViewModule: SearchViewModule
) : PagingSource<Int, ListFilmDto>() {
    override fun getRefreshKey(state: PagingState<Int, ListFilmDto>): Int? =
        ConstantString.FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListFilmDto> {
        val paging = params.key ?: ConstantString.FIRST_PAGE
        kotlin.runCatching {
            if (paging == 1) state.value = State.Loading

            searchViewModule.listFilmFilter(viewModel, paging)
        }.fold(
            onSuccess = {
                state.value = State.Success
                return LoadResult.Page(
                    data = it!!.items,
                    prevKey = null,
                    nextKey = if (it.items.isEmpty()) null else paging + 1
                )
            },
            onFailure = {
                state.value = State.Error
                return LoadResult.Error(it)
            }
        )
    }
}