package com.example.final_android_lvl1.presentetion.basic.home.person

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainItemCollectionPersonFilmBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel

class AdapterPersonFilm(
    private val listFilm: List<ListFilmDto>,
    private val viewModel: MainViewModel,
    private val onClickFilm: (id: Int) -> Unit,
) : RecyclerView.Adapter<WorkerFilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerFilmViewHolder {
        val binding =
            MainItemCollectionPersonFilmBinding.inflate(LayoutInflater.from(parent.context))
        return WorkerFilmViewHolder(binding)
    }

    override fun getItemCount(): Int = listFilm.size

    override fun onBindViewHolder(holder: WorkerFilmViewHolder, position: Int) {
        val index = listFilm[position]
        viewModel.workerFilms(holder.binding, index, onClickFilm)
    }

}

class WorkerFilmViewHolder(val binding: MainItemCollectionPersonFilmBinding) :
    RecyclerView.ViewHolder(binding.root)