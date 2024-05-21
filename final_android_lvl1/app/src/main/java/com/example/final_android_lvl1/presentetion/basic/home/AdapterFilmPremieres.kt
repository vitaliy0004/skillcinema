package com.example.final_android_lvl1.presentetion.basic.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainItemPreviewFilmBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule

class AdapterFilmPremieres(
    private val profileViewModule: ProfileViewModule,
    private val listFilm: List<ListFilmDto>,
    private val viewModule: MainViewModel,
    private val onClickAll: () -> Unit,
    private val onClickFilm: (Int) -> Unit,
    private val onClickViewed: (ListFilmDto) -> Unit,
    private val onClickNotViewed: (ListFilmDto) -> Unit
) : RecyclerView.Adapter<PreviewListFilmViewHolder>() {
    private val collectionController = viewModule.controllerCollection
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewListFilmViewHolder {
        val binding =
            MainItemPreviewFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreviewListFilmViewHolder(binding)
    }

    override fun getItemCount(): Int = if (listFilm.size < 20) {
        listFilm.size
    } else 21

    override fun onBindViewHolder(holder: PreviewListFilmViewHolder, position: Int) {
        if (20 == position) {
            with(holder.binding) {
                layoutPreviewFilm.visibility = View.INVISIBLE
                layoutNext.visibility = View.VISIBLE
            }
        } else {
            val index = listFilm[position]
            with(holder.binding) {
                layoutPreviewFilm.visibility = View.VISIBLE
                layoutNext.visibility = View.INVISIBLE
                viewModule.imageFilm(
                    profileViewModule,
                    previewFilm,
                    index,
                    onClickFilm,
                    onClickViewed,
                    onClickNotViewed
                )
                nextOpen.imageView.setOnClickListener {
                    viewModule.controllerCollection = collectionController
                    onClickAll()
                }
            }
        }
    }
}

class PreviewListFilmViewHolder(val binding: MainItemPreviewFilmBinding) :
    RecyclerView.ViewHolder(binding.root)