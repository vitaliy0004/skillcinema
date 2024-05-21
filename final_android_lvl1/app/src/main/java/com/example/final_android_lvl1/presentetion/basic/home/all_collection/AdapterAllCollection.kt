package com.example.final_android_lvl1.presentetion.basic.home.all_collection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainItemPreviewFilmItemBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule

class AdapterAllCollection(
    private val profileViewModule: ProfileViewModule,
    private val viewModel: MainViewModel,
    private val onClickFilm: (id: Int) -> Unit,
    private val onClickViewed: (ListFilmDto) -> Unit,
    private val onClickNotViewed: (ListFilmDto) -> Unit
) : PagingDataAdapter<ListFilmDto, CollectionViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding =
            MainItemPreviewFilmItemBinding.inflate(LayoutInflater.from(parent.context))
        return CollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val index = getItem(position)!!
        with(holder.binding) {
            viewModel.imageFilm(
                profileViewModule,
                this,
                index,
                onClickFilm,
                onClickViewed,
                onClickNotViewed
            )
            view.visibility = View.GONE
            viewOf.visibility = View.GONE
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<ListFilmDto>() {
    override fun areContentsTheSame(oldItem: ListFilmDto, newItem: ListFilmDto): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId


    override fun areItemsTheSame(oldItem: ListFilmDto, newItem: ListFilmDto): Boolean =
        oldItem == newItem
}

class CollectionViewHolder(val binding: MainItemPreviewFilmItemBinding) :
    RecyclerView.ViewHolder(binding.root)

