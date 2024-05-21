package com.example.final_android_lvl1.presentetion.basic.home.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.databinding.MainFragmentGalleryPhotoBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel

class GalleryPhotoFragment : Fragment() {
    private var _binding: MainFragmentGalleryPhotoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentGalleryPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bottomNavigation[viewModel.counterBottomNavigation]!!.add(R.id.galleryPhoto)
        viewModel.isBack = false
        viewModel.backPressed(requireActivity(), viewLifecycleOwner, R.id.galleryPhoto)
        binding.back.setOnClickListener {
            viewModel.backArrow(R.id.galleryPhoto, requireActivity())
        }
        binding.imageView.adapter =
            AdapterPhoto(viewModel.listPhotoGallery[viewModel.counterBottomNavigation]!!)
        binding.imageView.setCurrentItem(
            viewModel.photoPosition[viewModel.counterBottomNavigation]!!,
            false
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}