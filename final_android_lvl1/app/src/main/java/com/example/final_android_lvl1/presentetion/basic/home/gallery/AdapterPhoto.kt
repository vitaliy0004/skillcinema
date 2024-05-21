package com.example.final_android_lvl1.presentetion.basic.home.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.final_android_lvl1.data.retrofit.dto.detail.gallery.ItemGalleryDto
import com.example.final_android_lvl1.databinding.MainItemGalleryPhotoBinding

class AdapterPhoto(
    private val listGallery: List<ItemGalleryDto>
) : RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            MainItemGalleryPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun getItemCount(): Int = listGallery.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        Glide
            .with(holder.binding.imageView)
            .load(listGallery[position].imageUrl)
            .into(holder.binding.imageView)

    }
}

class PhotoViewHolder(val binding: MainItemGalleryPhotoBinding) :
    RecyclerView.ViewHolder(binding.root)