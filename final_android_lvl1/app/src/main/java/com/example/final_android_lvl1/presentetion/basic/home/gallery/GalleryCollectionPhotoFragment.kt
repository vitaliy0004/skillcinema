package com.example.final_android_lvl1.presentetion.basic.home.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.data.retrofit.dto.detail.gallery.ItemGalleryDto
import com.example.final_android_lvl1.databinding.MainFragmentGalleryCollectionPhotoBinding
import com.example.final_android_lvl1.databinding.MainFragmentGalleryPhotoBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.home.detail.DetailViewModel
import com.example.final_android_lvl1.presentetion.basic.home.detail.FactoryDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GalleryCollectionPhotoFragment : Fragment() {
    private var _binding: MainFragmentGalleryCollectionPhotoBinding? = null
    private val binding get() = _binding!!
    private val viewModule: MainViewModel by activityViewModels()
    lateinit var adapterPhoto: AdapterCollection

    @Inject
    lateinit var factory: FactoryDetail
    private val detailViewModel: DetailViewModel by viewModels { factory }
    private var collectionPhotosFromShooting: List<ItemGalleryDto>? = null
    private var collectionPosters: List<ItemGalleryDto>? = null
    lateinit var navigation: (Int, list: List<ItemGalleryDto>) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentGalleryCollectionPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModule.isBack)
            viewModule.bottomNavigation[viewModule.counterBottomNavigation]!!.add(R.id.galleryCollectionPhoto)
        viewModule.isBack = false
        viewModule.backPressed(requireActivity(), viewLifecycleOwner, R.id.galleryCollectionPhoto)
        binding.nameCollectionPhoto.collectionName.text = getString(R.string.gallery)
        binding.nameCollectionPhoto.back.setOnClickListener {
            viewModule.backArrow(R.id.galleryCollectionPhoto, requireActivity())
        }

        navigation = { position: Int, list: List<ItemGalleryDto> ->
            viewModule.photoDetail(
                position,
                findNavController().navigate(R.id.action_galleryCollectionPhoto_to_galleryPhoto),
                list
            )
        }
        binding.listPhoto.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position % 3 == 0) 2 else 1
                    }
                }
            }

        adapterPhoto = AdapterCollection(
            navigation,
            viewModule.listPhotoGallery[viewModule.counterBottomNavigation]!!
        )
        binding.listPhoto.adapter = adapterPhoto

        binding.photosFromShooting.setOnClickListener {
            binding.personnel.isClickable = true
            binding.posters.isClickable = true
            binding.photosFromShooting.isClickable = false
            lifecycleScope.launch {
                if (collectionPhotosFromShooting == null) isOnline(getString(R.string.shooting))
                else
                    collectionGallery(
                        collectionPhotosFromShooting!!,
                        getString(R.string.no_photo_shooting),
                        navigation
                    )
            }
        }
        binding.posters.setOnClickListener {
            binding.personnel.isClickable = true
            binding.photosFromShooting.isClickable = true
            binding.posters.isClickable = false
            lifecycleScope.launch {
                if (collectionPosters == null) {
                    isOnline(getString(R.string.poster))
                } else
                    collectionGallery(
                        collectionPosters!!,
                        getString(R.string.no_poster),
                        navigation
                    )

            }
        }
        binding.personnel.setOnClickListener {
            binding.photosFromShooting.isClickable = true
            binding.posters.isClickable = true
            binding.personnel.isClickable = false

            viewModule.listPhotoGallery[viewModule.counterBottomNavigation] =
                viewModule.listGallery[viewModule.idFilm[viewModule.counterBottomNavigation]!!.last()]!!
            adapterPhoto = AdapterCollection(
                navigation,
                viewModule.listPhotoGallery[viewModule.counterBottomNavigation]!!
            )
            binding.listPhoto.adapter = adapterPhoto
            binding.nameCollectionPhoto.collectionName.text = getString(R.string.gallery)
        }
    }

    private fun collectionGallery(
        listCollection: List<ItemGalleryDto>,
        nameCollection: String,
        navigation: (Int, List<ItemGalleryDto>) -> Unit
    ) {
        if (listCollection.isEmpty())
            binding.nameCollectionPhoto.collectionName.text = nameCollection
        else {
            adapterPhoto = AdapterCollection(navigation, listCollection)
            binding.listPhoto.adapter = adapterPhoto
            viewModule.listPhotoGallery[viewModule.counterBottomNavigation] = listCollection
            binding.nameCollectionPhoto.collectionName.text = getString(R.string.gallery)
        }
    }

    private suspend fun isOnline(sort: String) {
        if (viewModule.isOnline(requireContext())) {
            binding.listPhoto.visibility = View.INVISIBLE
            binding.layoutWifi.visibility = View.INVISIBLE
            binding.progressbar.visibility = View.VISIBLE

            if (sort == getString(R.string.poster)) {
                collectionPosters = detailViewModel.gallery(
                    viewModule.idFilm[viewModule.counterBottomNavigation]!!.last(),
                    sort
                )
                collectionGallery(collectionPosters!!, getString(R.string.no_poster), navigation)
            } else {
                collectionPhotosFromShooting = detailViewModel.gallery(
                    viewModule.idFilm[viewModule.counterBottomNavigation]!!.last(),
                    sort
                )
                collectionGallery(
                    collectionPhotosFromShooting!!,
                    getString(R.string.no_photo_shooting),
                    navigation
                )
            }

            binding.listPhoto.visibility = View.VISIBLE
            binding.progressbar.visibility = View.INVISIBLE
        } else {
            binding.listPhoto.visibility = View.INVISIBLE
            binding.layoutWifi.visibility = View.VISIBLE
        }
        binding.wifi.textView.setOnClickListener {
            lifecycleScope.launch {
                isOnline(sort)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}