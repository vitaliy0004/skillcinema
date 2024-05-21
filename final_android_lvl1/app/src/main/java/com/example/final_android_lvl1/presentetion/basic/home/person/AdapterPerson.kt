package com.example.final_android_lvl1.presentetion.basic.home.person

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.final_android_lvl1.data.retrofit.dto.detail.FilmParticipantsDto
import com.example.final_android_lvl1.databinding.MainItemPreviewFilmItemBinding
import com.example.final_android_lvl1.presentetion.basic.home.all_collection.CollectionViewHolder

class AdapterPerson(
    private val listPerson: List<FilmParticipantsDto>,
    private val onClickPerson: (id: Int) -> Unit
) : RecyclerView.Adapter<CollectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding =
            MainItemPreviewFilmItemBinding.inflate(LayoutInflater.from(parent.context))
        return CollectionViewHolder(binding)
    }

    override fun getItemCount(): Int = listPerson.size

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val index = listPerson[position]
        with(holder.binding) {
            view.visibility = View.INVISIBLE
            itemFilmRating.visibility = View.INVISIBLE
            Glide.with(imageView)
                .load(index.posterUrl)
                .into(imageView)
            val name = if (index.nameRu == null) index.nameEn
            else index.nameRu
            val profession = if (index.professionText == null) index.professionKey
            else index.professionText
            nameFilm.text =
                if (name.length > 20) name.filterIndexed { index, c -> index < 17 } + "..."
                else name
            genre.text =
                if (profession.length > 20) profession.filterIndexed { index, c -> index < 17 } + "..."
                else profession

            collection.setOnClickListener {
                onClickPerson(index.staffId)
            }
        }
    }
}