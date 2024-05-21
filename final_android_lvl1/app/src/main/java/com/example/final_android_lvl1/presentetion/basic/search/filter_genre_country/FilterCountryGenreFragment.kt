package com.example.final_android_lvl1.presentetion.basic.search.filter_genre_country

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.databinding.MainFragmentFilterCountryGenreBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FilterCountryGenreFragment : Fragment() {
    private var _binding: MainFragmentFilterCountryGenreBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: ArrayAdapter<String>
    private val viewModule: MainViewModel by activityViewModels()

    @Inject
    lateinit var factory: FactoryAppParamsViewModule
    private val addParamsFilterViewModule: AddParamsFilterViewModule by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentFilterCountryGenreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModule.bottomNavigation[2]!!.add(R.id.filterCountryGenreFragment)
        viewModule.backPressed(
            requireActivity(),
            viewLifecycleOwner,
            R.id.filterCountryGenreFragment
        )

        if (viewModule.countryAndGenre == null) {
            lifecycleScope.launch { isOnline() }
        } else {
            binding.layoutWifi.visibility = View.INVISIBLE
            addParamsFilterViewModule.sortedList(viewModule.countryAndGenre!!)
            defaultBinding()
        }
        binding.nameCollection.back.setOnClickListener {
            viewModule.backArrow(R.id.filterCountryGenreFragment, requireActivity())
        }
    }

    private suspend fun isOnline() {
        if (viewModule.isOnline(requireContext())) {
            binding.list.visibility = View.INVISIBLE
            binding.progressbar.visibility = View.VISIBLE
            binding.layoutWifi.visibility = View.INVISIBLE

            viewModule.countryAndGenre = addParamsFilterViewModule.countryAndGenre()
            addParamsFilterViewModule.sortedList(viewModule.countryAndGenre!!)
            defaultBinding()

            binding.list.visibility = View.VISIBLE
            binding.progressbar.visibility = View.INVISIBLE
        } else {
            binding.list.visibility = View.INVISIBLE
            binding.layoutWifi.visibility = View.VISIBLE
        }
        binding.noWifi.textView.setOnClickListener {
            lifecycleScope.launch { isOnline() }
        }
    }

    private fun defaultBinding() {
        if (viewModule.isCountry) {
            default(addParamsFilterViewModule.listCountry, getString(R.string.countries_english))
            binding.nameCollection.collectionName.text = getString(R.string.countries)
            binding.searchMyField.addTextChangedListener {
                filterParams(
                    addParamsFilterViewModule.listCountry,
                    getString(R.string.countries_english)
                )
            }
        } else {
            default(addParamsFilterViewModule.listGenre, getString(R.string.genres_english))
            binding.nameCollection.collectionName.text = getString(R.string.genre)
            binding.searchMyField.addTextChangedListener {
                filterParams(
                    addParamsFilterViewModule.listGenre,
                    getString(R.string.genres_english)
                )
            }
        }
    }

    private fun adapter(list: List<String>) {
        adapter = addParamsFilterViewModule.adapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            list
        )
        binding.list.adapter = adapter
    }

    private fun default(list: List<String>, genreAndCountry: String) {
        adapter(list)
        binding.list.setOnItemClickListener { parent, view, position, id ->
            if (genreAndCountry == getString(R.string.countries)) {
                viewModule.filterMapInt[genreAndCountry] =
                    addParamsFilterViewModule.mapCountry[list[position]]
                viewModule.filterCountryAndGenre[0] = list[position]
            } else {
                viewModule.filterCountryAndGenre[1] = list[position]
                viewModule.filterMapInt[genreAndCountry] =
                    addParamsFilterViewModule.mapGenre[list[position]]
            }
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }
    }

    private fun filterParams(list: List<String>, genreAndCountry: String) {
        if (binding.searchMyField.text?.length == 0) {
            default(list, genreAndCountry)
        } else {
            adapter(
                addParamsFilterViewModule.filterParams(
                    binding.searchMyField.text!!.toString(),
                    list
                )
            )
            binding.list.setOnItemClickListener { parent, view, position, id ->
                if (genreAndCountry == getString(R.string.countries_english)) {
                    viewModule.filterMapInt[genreAndCountry] =
                        addParamsFilterViewModule.mapCountry[addParamsFilterViewModule.filter[position]]!!
                    viewModule.filterCountryAndGenre[0] = addParamsFilterViewModule.filter[position]
                } else {
                    viewModule.filterMapInt[genreAndCountry] =
                        addParamsFilterViewModule.mapGenre[addParamsFilterViewModule.filter[position]]!!
                    viewModule.filterCountryAndGenre[1] = addParamsFilterViewModule.filter[position]
                }
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}