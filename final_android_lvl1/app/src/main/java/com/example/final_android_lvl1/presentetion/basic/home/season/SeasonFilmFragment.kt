package com.example.final_android_lvl1.presentetion.basic.home.season

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.databinding.MainFragmentSeasonFilmBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.google.android.material.chip.Chip


class SeasonFilmFragment : Fragment() {
    private var _binding: MainFragmentSeasonFilmBinding? = null
    private val binding get() = _binding!!
    private val viewModule: MainViewModel by activityViewModels()
    private val seasonViewModule: SeasonViewModule by viewModels()
    private lateinit var adapterSeason: AdapterSeason

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentSeasonFilmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModule.isBack)
            viewModule.bottomNavigation[viewModule.counterBottomNavigation]!!.add(R.id.seasonFilmFragment)
        viewModule.isBack = false
        viewModule.backPressed(requireActivity(), viewLifecycleOwner, R.id.seasonFilmFragment)
        val detailFilm =
            viewModule.listDetailFilm[viewModule.idFilm[viewModule.counterBottomNavigation]!!.last()]!!
        val seasonFilm =
            viewModule.listEpisode[viewModule.idFilm[viewModule.counterBottomNavigation]!!.last()]!!
        binding.barName.collectionName.text = seasonViewModule.nameCollection(detailFilm)
        seasonViewModule.sortSeason(seasonFilm)
        createChip()
        binding.barName.back.setOnClickListener {
            viewModule.backArrow(R.id.seasonFilmFragment, requireActivity())
        }
    }

    private fun createChip() {
        var start = true
        seasonViewModule.listSeason.forEach {
            val chip = Chip(requireContext())
            chip.text = it.toString()
            binding.linearLayoutCompat.addView(chip)
            chip.isCheckable = true
            if (start) {
                chip.isChecked = true
                newAdapter(chip, it)
            } else chip.isChecked = false
            start = false
            chip.setOnClickListener { _ ->
                binding.linearLayoutCompat.forEach { chipAll -> chipAll.isClickable = true }
                newAdapter(chip, it)
            }
        }
        binding.linearLayoutCompat[0].isClickable = false
    }

    private fun newAdapter(chip: Chip, counter: Int) {
        chip.isClickable = false
        binding.textSeasonAndEpisodes.text = getString(
            R.string.text_season_and_episodes,
            counter,
            seasonViewModule.mapEpisode[counter]!!.size
        )
        adapterSeason = AdapterSeason(
            seasonViewModule.mapEpisode[counter]!!,
            seasonViewModule
        )
        binding.recyclerEpisodes.adapter = adapterSeason
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}