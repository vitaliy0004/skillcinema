package com.example.final_android_lvl1.presentetion.basic.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainDialogItemBinding
import com.example.final_android_lvl1.entity.database.AllInfoDatabase

class AdapterCollectionCheckbox(
    private val listAllInfoDatabase: List<AllInfoDatabase>,
    private val film: ListFilmDto,
    private val onClickTrue: (ListFilmDto, Int) -> Unit,
    private val onClickFalse: (ListFilmDto, Int) -> Unit,
) : RecyclerView.Adapter<CheckboxFilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckboxFilmViewHolder {
        val binding =
            MainDialogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckboxFilmViewHolder(binding)
    }

    override fun getItemCount(): Int = listAllInfoDatabase.size

    override fun onBindViewHolder(holder: CheckboxFilmViewHolder, position: Int) {
        val index = listAllInfoDatabase[position]
        val idFilm = if (film.kinopoiskId == 0) film.filmId
        else film.kinopoiskId
        var isCheckedCollection = false
        index.listFilm!!.forEach {
            if (it.kinopoiskId == idFilm) isCheckedCollection = true
        }
        holder.binding.counter.text = index.listFilm.size.toString()
        holder.binding.textView2.text = index.collectionFilm.nameCollection
        holder.binding.checkBox.isChecked = isCheckedCollection
        holder.binding.checkBox.setOnClickListener {
            if (!holder.binding.checkBox.isChecked) onClickFalse(
                film,
                index.collectionFilm.idCollection!!
            )
            else onClickTrue(film, index.collectionFilm.idCollection!!)
        }
    }
}

class CheckboxFilmViewHolder(val binding: MainDialogItemBinding) :
    RecyclerView.ViewHolder(binding.root)