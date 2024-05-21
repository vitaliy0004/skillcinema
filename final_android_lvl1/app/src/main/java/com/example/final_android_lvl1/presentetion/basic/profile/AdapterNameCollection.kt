package com.example.final_android_lvl1.presentetion.basic.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.databinding.MainItemCollectionSafelyBinding
import com.example.final_android_lvl1.entity.database.AllInfoDatabase

class AdapterNameCollection(
    private val listCollection: List<AllInfoDatabase>,
    private val onClickDelete: (id: Int) -> Unit,
    private val onClickCollection: (id: Int, nameCollection: String) -> Unit
) : RecyclerView.Adapter<NameCollectionFilmViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NameCollectionFilmViewHolder {
        val binding = MainItemCollectionSafelyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NameCollectionFilmViewHolder(binding)
    }

    override fun getItemCount(): Int = listCollection.size

    override fun onBindViewHolder(holder: NameCollectionFilmViewHolder, position: Int) {
        val index = listCollection[position]
        with(holder.binding) {
            if (position == 0) {
                remove.visibility = View.INVISIBLE
                viewCollection.setImageDrawable(viewCollection.resources.getDrawable(R.drawable.favorite))

            } else if (position == 1) {
                remove.visibility = View.INVISIBLE
                viewCollection.setImageDrawable(viewCollection.resources.getDrawable(R.drawable.bookmark))
            } else {
                viewCollection.setImageDrawable(viewCollection.resources.getDrawable(R.drawable.person))
            }
            textNameCollection.text = index.collectionFilm.nameCollection
            counterCollection.text = index.listFilm!!.size.toString()
            remove.setOnClickListener {
                onClickDelete(index.collectionFilm.idCollection!!)
            }
            collectionView.setOnClickListener {
                onClickCollection(
                    index.collectionFilm.idCollection!!,
                    index.collectionFilm.nameCollection
                )
            }

        }

    }
}

class NameCollectionFilmViewHolder(val binding: MainItemCollectionSafelyBinding) :
    RecyclerView.ViewHolder(binding.root)