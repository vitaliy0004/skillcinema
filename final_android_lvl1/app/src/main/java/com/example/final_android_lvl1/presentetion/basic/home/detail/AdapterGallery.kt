package com.example.final_android_lvl1.presentetion.basic.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.final_android_lvl1.data.retrofit.dto.detail.gallery.ItemGalleryDto
import com.example.final_android_lvl1.databinding.MainItemGalleryPreviewBinding

class AdapterGallery(
    private val onClickPhoto: (Int, List<ItemGalleryDto>) -> Unit,
    private val listGallery: List<ItemGalleryDto>
) : RecyclerView.Adapter<GalleryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = MainItemGalleryPreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GalleryViewHolder(binding)
    }

    override fun getItemCount(): Int = if (listGallery.size > 20) 20
    else listGallery.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
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

class GalleryViewHolder(val binding: MainItemGalleryPreviewBinding) :
    RecyclerView.ViewHolder(binding.root)