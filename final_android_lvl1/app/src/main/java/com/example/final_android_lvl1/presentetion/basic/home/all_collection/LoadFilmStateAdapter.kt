package com.example.final_android_lvl1.presentetion.basic.home.all_collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_android_lvl1.databinding.MainItemProgressBinding

class LoadFilmStateAdapter : LoadStateAdapter<LoadProgressStateAdapter>() {
    override fun onBindViewHolder(holder: LoadProgressStateAdapter, loadState: LoadState) = Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadProgressStateAdapter {
        val binding =
            MainItemProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadProgressStateAdapter(binding)
    }
}

class LoadProgressStateAdapter(binding: MainItemProgressBinding) :
    RecyclerView.ViewHolder(binding.root)