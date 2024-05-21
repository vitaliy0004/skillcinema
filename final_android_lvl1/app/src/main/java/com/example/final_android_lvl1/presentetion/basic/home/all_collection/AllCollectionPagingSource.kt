package com.example.final_android_lvl1.presentetion.basic.home.all_collection

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.presentetion.ConstantString
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.home.HomeViewModel
import com.example.final_android_lvl1.presentetion.basic.search.State
import kotlinx.coroutines.flow.MutableStateFlow

class AllCollectionPagingSource(
    private val state: MutableStateFlow<State>,
    private val viewModel: MainViewModel,
    private val homeViewModel: HomeViewModel,
    private val context: Context
) : PagingSource<Int, ListFilmDto>() {


    override fun getRefreshKey(state: PagingState<Int, ListFilmDto>): Int? =
        ConstantString.FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListFilmDto> {
        val paging = params.key ?: ConstantString.FIRST_PAGE
        kotlin.runCatching {
            viewModel.isOnline(context)
            val getPrefers =
                context.getSharedPreferences(ConstantString.FILM_COLLECTION, Context.MODE_PRIVATE)!!
            if (paging == 1) state.value = State.Loading
            homeViewModel.controllerCollection(
                paging,
                getPrefers.getInt(ConstantString.COUNTER_COLLECTION, 0),
                getPrefers.getInt(ConstantString.COUNTRY1, 0),
                getPrefers.getInt(ConstantString.COUNTRY2, 0),
                getPrefers.getInt(ConstantString.GENRE1, 0),
                getPrefers.getInt(ConstantString.GENRE2, 0),
                viewModel
            )

        }.fold(
            onSuccess = {
                state.value = State.Success
                return LoadResult.Page(
                    data = if (it.items == null) it.films else it.items,
                    prevKey = null,
                    nextKey = if (it.items == null) {
                        if (it.films.isEmpty()) null else paging + 1
                    } else if (it.items.isEmpty()) null else paging + 1
                )
            },
            onFailure = {
                state.value = State.Error
                return LoadResult.Error(it)
            }
        )
    }

}