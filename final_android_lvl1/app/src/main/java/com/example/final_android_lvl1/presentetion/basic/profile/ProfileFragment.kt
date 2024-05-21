package com.example.final_android_lvl1.presentetion.basic.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainDialogCollectionBinding
import com.example.final_android_lvl1.databinding.MainFragmentProfileBinding
import com.example.final_android_lvl1.entity.database.AllInfoDatabase
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: MainFragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModule: MainViewModel by activityViewModels()
    private lateinit var adapterFilm: AdapterFilmAndClear
    private lateinit var adapterCollection: AdapterNameCollection
    private val listCollection = mutableListOf<AllInfoDatabase>()
    private lateinit var listFilm: List<ListFilmDto>
    private val profileViewModule: ProfileViewModule by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModule.isBack = false
        viewModule.backPressed(requireActivity(), viewLifecycleOwner, R.id.profileFragments)

        binding.viewed.nameCollection.text.text = getString(R.string.view)
        binding.wasInteresting.nameCollection.text.text = getString(R.string.were_you_interested)
        binding.viewed.nameCollection.layoutTableOfContents.setOnClickListener {
            allCollectionFilm(1, getString(R.string.view))
        }
        binding.wasInteresting.nameCollection.layoutTableOfContents.setOnClickListener {
            allCollectionFilm(2, getString(R.string.were_you_interested))
        }
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            binding.infoProfile.visibility = View.INVISIBLE
            adapterViewed()
            adapterWasInteresting()
            binding.progressBar.visibility = View.INVISIBLE
            binding.infoProfile.visibility = View.VISIBLE
            profileViewModule.allCollection.collect {
                listCollection.clear()
                it.forEachIndexed { index, allInfoDatabase ->
                    if (index > 1) listCollection.add(allInfoDatabase)
                }
                adapterCollection = AdapterNameCollection(
                    listCollection,
                    { id -> profileViewModule.removeCollection(id) },
                    { id, nameCollection -> allCollectionFilm(id, nameCollection) }
                )
                binding.collectionCollection.adapter = adapterCollection

            }


        }
        binding.addCollection.setOnClickListener {
            addCollection()
        }
    }

    private fun allCollectionFilm(idCollection: Int, nameCollection: String) {
        lifecycleScope.launch {
            val listFilm = mutableListOf<ListFilmDto>()
            profileViewModule.saveCollection(idCollection).forEach {
                listFilm.add(it)
            }
            viewModule.listFilm[viewModule.counterBottomNavigation]!!.add(listFilm)
            viewModule.nameCollection[viewModule.counterBottomNavigation]!!.add(nameCollection)
            findNavController().navigate(R.id.action_profileFragments_to_collectionFilm)
        }

    }


    private fun addCollection() {
        val dialog = BottomSheetDialog(requireContext())
        val bindingDialog = MainDialogCollectionBinding.inflate(layoutInflater)
        bindingDialog.close.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.finish.setOnClickListener {
            profileViewModule.addCollection(
                bindingDialog.nameCollection.text.toString(),
            )
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(bindingDialog.root)
        dialog.show()
    }

    private fun clearViewed() {
        lifecycleScope.launch {
            profileViewModule.removeCollectionFilm(1)
            adapterViewed()
        }
    }

    private suspend fun adapterViewed() {
        profileViewModule._listViewedFilm.value.clear()
        profileViewModule.saveCollection(1).forEach {
            profileViewModule._listViewedFilm.value.add(it)
        }
        profileViewModule.viewCounter(
            binding.viewed.nameCollection,
            profileViewModule.listViewedFilm.value.toList()
        )
        adapterFilm = AdapterFilmAndClear(
            profileViewModule,
            profileViewModule.listViewedFilm.value.toList(),
            viewModule,
            { clearViewed() },
            { id -> detail(id) },
            { listFilm -> addFilmViewed(listFilm) },
            { listFilm -> removeFilmViewed(listFilm) }
        )
        binding.viewed.collection.adapter = adapterFilm
    }

    private suspend fun adapterWasInteresting() {
        listFilm = profileViewModule.saveCollection(2)
        profileViewModule.viewCounter(binding.wasInteresting.nameCollection, listFilm)
        adapterFilm = AdapterFilmAndClear(
            profileViewModule,
            listFilm,
            viewModule,
            { clearWasInteresting() },
            { id -> detail(id) },
            { listFilm -> addFilmViewed(listFilm) },
            { listFilm -> removeFilmViewed(listFilm) }
        )
        binding.wasInteresting.collection.adapter = adapterFilm

    }

    private fun removeFilmViewed(film: ListFilmDto) {
        lifecycleScope.launch {
            profileViewModule.removeFilm(film, 1)
            adapterViewed()
        }

    }

    private fun addFilmViewed(film: ListFilmDto) {
        lifecycleScope.launch {
            profileViewModule.addFim(film, 1)
            adapterViewed()
        }

    }

    private fun clearWasInteresting() {
        lifecycleScope.launch {
            profileViewModule.removeCollectionFilm(2)
            adapterWasInteresting()
        }
    }

    private fun detail(id: Int) {
        viewModule.idFilm[viewModule.counterBottomNavigation]!!.add(id)
        findNavController().navigate(R.id.action_profileFragments_to_detailFilmFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}