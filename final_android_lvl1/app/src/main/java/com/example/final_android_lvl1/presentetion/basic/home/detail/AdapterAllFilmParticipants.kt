package com.example.final_android_lvl1.presentetion.basic.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.final_android_lvl1.data.retrofit.dto.detail.FilmParticipantsDto
import com.example.final_android_lvl1.databinding.MainItemActorsBinding

class AdapterAllFilmParticipants(
    private val listAllFilmParticipants: List<FilmParticipantsDto>,
    private val onClickPerson: (Int) -> Unit
) : RecyclerView.Adapter<AllFilmParticipantsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllFilmParticipantsViewHolder {
        val binding =
            MainItemActorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllFilmParticipantsViewHolder(binding)
    }

    override fun getItemCount(): Int = if (listAllFilmParticipants.size > 20) 20
    else listAllFilmParticipants.size

    override fun onBindViewHolder(holder: AllFilmParticipantsViewHolder, position: Int) {
        val index = listAllFilmParticipants[position]

        with(holder.binding) {
            if (index.nameRu == null) actorsName.text = index.nameEn
            else actorsName.text = index.nameRu
            if (index.professionText == null) character.text = index.professionKey
            else character.text = index.professionText
            Glide
                .with(actorsPhoto)
                .load(index.posterUrl)
                .into(actorsPhoto)
            personLayout.setOnClickListener {
                onClickPerson(index.staffId)
            }
        }
    }
}

class AllFilmParticipantsViewHolder(val binding: MainItemActorsBinding) :
    RecyclerView.ViewHolder(binding.root)