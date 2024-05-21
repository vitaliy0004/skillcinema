package com.example.final_android_lvl1.presentetion.basic.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.databinding.MainFragmentSearchFilterFilmBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel


class FilterFilmFragment : Fragment() {
    private var _binding: MainFragmentSearchFilterFilmBinding? = null
    private val binding get() = _binding!!
    private val viewModule: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentSearchFilterFilmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModule.isBack) viewModule.bottomNavigation[2]!!.add(R.id.filterFilmFragment)
        viewModule.isBack = false
        viewModule.backPressed(requireActivity(), viewLifecycleOwner, R.id.filterFilmFragment)
        standardParameters()

        binding.searchRadioFilms.setOnClickListener {
            viewModule.filterMapString["type"] = "FILM"
        }
        binding.searchRadioAll.setOnClickListener {
            viewModule.filterMapString["type"] = "ALL"
        }
        binding.searchRadioSeries.setOnClickListener {
            viewModule.filterMapString["type"] = "TV_SERIES"
        }
        binding.nameCollection.back.setOnClickListener {
            viewModule.backArrow(R.id.filterFilmFragment, requireActivity())
        }
        binding.country.mainItemSearchParams.setOnClickListener {
            viewModule.isCountry = true
            findNavController().navigate(R.id.action_filterFilmFragment_to_filterCountryGenreFragment)
        }
        binding.genre.mainItemSearchParams.setOnClickListener {
            viewModule.isCountry = false
            findNavController().navigate(R.id.action_filterFilmFragment_to_filterCountryGenreFragment)
        }
        binding.years.mainItemSearchParams.setOnClickListener {
            findNavController().navigate(R.id.action_filterFilmFragment_to_yearFilterFragment)
        }
        binding.searchSettingsRatingSlider.addOnChangeListener { slider, value, fromUser ->
            binding.maxRating.text = slider.values[1].toInt().toString()
            binding.minRating.text = slider.values[0].toInt().toString()
            viewModule.filterMapInt["ratingFrom"] = slider.values[0].toInt()
            viewModule.filterMapInt["ratingTo"] = slider.values[1].toInt()
            if (slider.values[0].toInt() == 1 && slider.values[0].toInt() == 10)
                binding.rating.filterParams.text = getString(R.string.any)
            binding.rating.filterParams.text = getString(
                R.string.period_film,
                slider.values[0].toInt(),
                slider.values[1].toInt()
            )
        }
        binding.viewedFilm.setOnClickListener {
            viewModule.isViewedFilm = binding.viewedFilm.isChecked
        }
        viewModule.filterMapString["keyword"] = null

        binding.sortData.setOnClickListener {
            viewModule.filterMapString["order"] = "YEAR"
        }
        binding.sortPopularity.setOnClickListener {
            viewModule.filterMapString["order"] = "NUM_VOTE"
        }
        binding.sortRating.setOnClickListener {
            viewModule.filterMapString["order"] = "RATING"
        }
    }

    private fun standardParameters() {
        binding.nameCollection.collectionName.text = getString(R.string.search_settings)
        binding.genre.parameter.text = getString(R.string.genre)
        binding.years.parameter.text = getString(R.string.years)
        binding.rating.parameter.text = getString(R.string.rating)
        binding.rating.viewLine.visibility = View.INVISIBLE
        binding.viewedFilm.isChecked = viewModule.isViewedFilm

        if (viewModule.filterMapInt["countries"] == null) binding.country.filterParams.text =
            getString(R.string.any)
        else binding.country.filterParams.text = viewModule.filterCountryAndGenre[0]

        if (viewModule.filterMapInt["genres"] == null) binding.genre.filterParams.text =
            getString(R.string.any)
        else binding.genre.filterParams.text = viewModule.filterCountryAndGenre[1]

        binding.years.filterParams.text = getString(
            R.string.period_film,
            viewModule.filterMapInt["yearFrom"],
            viewModule.filterMapInt["yearTo"]
        )

        if (viewModule.filterMapInt["ratingFrom"] == 1 && viewModule.filterMapInt["ratingTo"] == 10)
            binding.rating.filterParams.text = getString(R.string.any)
        else {
            binding.searchSettingsRatingSlider.values =
                listOf(
                    viewModule.filterMapInt["ratingFrom"]?.toFloat(),
                    viewModule.filterMapInt["ratingTo"]?.toFloat()
                )
            binding.minRating.text = viewModule.filterMapInt["ratingFrom"]!!.toString()
            binding.maxRating.text = viewModule.filterMapInt["ratingTo"]!!.toString()
            binding.rating.filterParams.text = getString(
                R.string.period_film,
                viewModule.filterMapInt["yearFrom"],
                viewModule.filterMapInt["yearTo"]
            )
        }

        when (viewModule.filterMapString["order"]) {
            "YEAR" -> binding.sortData.isChecked = true
            "NUM_VOTE" -> binding.sortPopularity.isChecked = true
            "RATING" -> binding.sortRating.isChecked = true
        }
        when (viewModule.filterMapString["type"]) {
            "TV_SERIES" -> binding.searchRadioSeries.isChecked = true
            "ALL" -> binding.searchRadioAll.isChecked = true
            "FILM" -> binding.searchRadioFilms.isChecked = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}