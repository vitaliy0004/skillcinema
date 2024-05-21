package com.example.final_android_lvl1.presentetion.basic.home.season

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_android_lvl1.data.retrofit.dto.serialDto.EpisodeDto
import com.example.final_android_lvl1.databinding.MainItemInfoEpisodeBinding

class AdapterSeason(
    private val episodes: List<EpisodeDto>,
    private val viewModel: SeasonViewModule
) : RecyclerView.Adapter<InfoEpisodeHolderView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoEpisodeHolderView {
        val binding = MainItemInfoEpisodeBinding.inflate(LayoutInflater.from(parent.context))
        return InfoEpisodeHolderView(binding)
    }

    override fun getItemCount(): Int = episodes.size

    override fun onBindViewHolder(holder: InfoEpisodeHolderView, position: Int) {
        val index = episodes[position]

        with(holder.binding) {
            val name = if (index.nameRu == null && index.nameEn == null) ""
            else if (index.nameRu == null) index.nameEn
            else index.nameRu
            episode.text = "${index.episodeNumber} серия. $name"
            data.text = if (index.releaseDate == null) "не извстна"
            else viewModel.data(index.releaseDate)
        }
    }
}

class InfoEpisodeHolderView(val binding: MainItemInfoEpisodeBinding) :
    RecyclerView.ViewHolder(binding.root)