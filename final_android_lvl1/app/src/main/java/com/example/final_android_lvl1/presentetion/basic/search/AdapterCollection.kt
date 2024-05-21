package com.example.final_android_lvl1.presentetion.basic.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainItemCollectionPersonFilmBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.home.all_collection.DiffUtilCallback
import com.example.final_android_lvl1.presentetion.basic.home.person.WorkerFilmViewHolder

class AdapterCollection(
    private val listFilm: List<ListFilmDto>,
    private val viewModel: MainViewModel,
    private val onClickFilm: (id: Int) -> Unit,
) : PagingDataAdapter<ListFilmDto, WorkerFilmViewHolder>(DiffUtilCallback()) {
    override fun onBindViewHolder(holder: WorkerFilmViewHolder, position: Int) {
        val index = getItem(position)!!
        var isCheckedFilm = false
        if (viewModel.isViewedFilm) {
            listFilm.forEach {
                if (it.kinopoiskId == index.kinopoiskId) isCheckedFilm = true
            }
        }

        if (isCheckedFilm) {
            with(holder.binding) {
                itemFilmRating.visibility = View.GONE
                nameFilm.visibility = View.GONE
                genre.visibility = View.GONE
                imageView.visibility = View.GONE
            }

        } else {
            viewModel.workerFilms(
                holder.binding,
                index,
                onClickFilm,
            )

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerFilmViewHolder {
        val binding =
            MainItemCollectionPersonFilmBinding.inflate(LayoutInflater.from(parent.context))
        return WorkerFilmViewHolder(binding)
    }
}