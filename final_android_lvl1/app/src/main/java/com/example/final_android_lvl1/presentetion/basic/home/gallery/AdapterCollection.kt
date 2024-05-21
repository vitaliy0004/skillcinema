package com.example.final_android_lvl1.presentetion.basic.home.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.final_android_lvl1.data.retrofit.dto.detail.gallery.ItemGalleryDto
import com.example.final_android_lvl1.databinding.MainItemGalleryCollectionBinding

class AdapterCollection(
    private val onClickPhoto: (Int, List<ItemGalleryDto>) -> Unit,
    private val listGallery: List<ItemGalleryDto>
) : RecyclerView.Adapter<PhotoCollectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoCollectionViewHolder {
        val binding = MainItemGalleryCollectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhotoCollectionViewHolder(binding)
    }

    override fun getItemCount(): Int = listGallery.size

    override fun onBindViewHolder(holder: PhotoCollectionViewHolder, position: Int) {
        val index = listGallery[position]
        Glide
            .with(holder.binding.photoGallery)
            .load(
                if (index.previewUrl == null) index.imageUrl
                else index.previewUrl
            )
            .into(holder.binding.photoGallery)
        holder.binding.photoGallery.setOnClickListener {

            onClickPhoto(position, listGallery)
        }
    }
}

class PhotoCollectionViewHolder(val binding: MainItemGalleryCollectionBinding) :
    RecyclerView.ViewHolder(binding.root)