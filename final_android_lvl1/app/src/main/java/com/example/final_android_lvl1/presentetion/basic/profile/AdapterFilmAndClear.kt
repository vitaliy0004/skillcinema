package com.example.final_android_lvl1.presentetion.basic.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainItemPreviewFilmBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.home.PreviewListFilmViewHolder

class AdapterFilmAndClear(
    private val profileViewModule: ProfileViewModule,
    private val listFilm: List<ListFilmDto>,
    private val viewModule: MainViewModel,
    private val onClickClear: () -> Unit,
    private val onClickFilm: (Int) -> Unit,
    private val onClickViewed: (ListFilmDto) -> Unit,
    private val onClickNotViewed: (ListFilmDto) -> Unit
) : RecyclerView.Adapter<PreviewListFilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewListFilmViewHolder {
        val binding =
            MainItemPreviewFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreviewListFilmViewHolder(binding)
    }

    override fun getItemCount(): Int = if (listFilm.size < 20) {
        listFilm.size + 1
    } else 21


    override fun onBindViewHolder(holder: PreviewListFilmViewHolder, position: Int) {
        if (listFilm.isEmpty()) {
            holder.binding.noCollectionFilm.visibility = View.VISIBLE
            holder.binding.layoutNext.visibility = View.INVISIBLE
            holder.binding.layoutPreviewFilm.visibility = View.INVISIBLE
            holder.binding.nextOpen.imageView.visibility = View.INVISIBLE

        } else {
            if (position >= listFilm.size || position == 20) {
                with(holder.binding) {
                    nextOpen.imageView.setImageDrawable(nextOpen.imageView.context.getDrawable(R.drawable.delete))
                    nextOpen.textView3.text = "очистить\nисторию"
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

                }
            }
            holder.binding.nextOpen.imageView.setOnClickListener {
                onClickClear()
            }
        }
    }
}