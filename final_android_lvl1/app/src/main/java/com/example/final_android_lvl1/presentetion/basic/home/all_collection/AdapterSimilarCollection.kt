package com.example.final_android_lvl1.presentetion.basic.home.all_collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainItemPreviewFilmItemBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule

class AdapterSimilarCollection(
    private val profileViewModule: ProfileViewModule,
    private val listFilm: List<ListFilmDto>,
    private val viewModel: MainViewModel,
    private val onClickFilm: (id: Int) -> Unit,
    private val onClickViewed: (ListFilmDto) -> Unit,
    private val onClickNotViewed: (ListFilmDto) -> Unit
) : RecyclerView.Adapter<CollectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding =
            MainItemPreviewFilmItemBinding.inflate(LayoutInflater.from(parent.context))
        return CollectionViewHolder(binding)
    }

    override fun getItemCount(): Int = listFilm.size

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val index = listFilm[position]
        viewModel.imageFilm(
            profileViewModule,
            holder.binding,
            index,
            onClickFilm,
            onClickViewed,
            onClickNotViewed
        )

    }
}