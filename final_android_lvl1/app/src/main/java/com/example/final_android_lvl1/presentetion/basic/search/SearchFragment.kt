package com.example.final_android_lvl1.presentetion.basic.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.databinding.MainFragmentSearchBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.home.all_collection.LoadFilmStateAdapter
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: MainFragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModule: MainViewModel by activityViewModels()

    @Inject
    lateinit var factory: SearchFactory
    private val searchViewModule: SearchViewModule by viewModels { factory }
    private lateinit var filmAdapter: AdapterCollection
    private val profileViewModule: ProfileViewModule by viewModels()
    private lateinit var job: Job


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModule.isBack = false
        viewModule.backPressed(requireActivity(), viewLifecycleOwner, R.id.searchFragments)
        searchViewModule.context = requireContext()
        searchFilter()

        binding.searchMyField.setOnClickListener {
            if (binding.searchMyField.text.toString() == "") viewModule.filterMapString["keyword"] =
                null
            else viewModule.filterMapString["keyword"] = binding.searchMyField.text.toString()
            searchFilter()
        }
        binding.searchFilterBtn.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragments_to_filterFilmFragment)
        }
        binding.noWifi.textView.setOnClickListener {
            filmAdapter.refresh()
        }
    }

    private fun searchFilter() {
        job = lifecycleScope.launch {
            val listViewedFilm = profileViewModule.saveCollection(1)
            searchViewModule.pagedFilter(viewModule)
            filmAdapter = AdapterCollection(
                listViewedFilm,
                viewModule,
                { id -> detailFilms(id) },
            )
            binding.recyclerFilm.adapter = filmAdapter.withLoadStateFooter(LoadFilmStateAdapter())
            searchViewModule.pagedFilter.onEach {
                filmAdapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            searchViewModule.state.collect { state ->
                when (state) {
                    State.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                        binding.recyclerFilm.visibility = View.INVISIBLE
                        binding.layoutWifi.visibility = View.INVISIBLE
                        binding.textNull.visibility = View.INVISIBLE
                    }

                    State.Error -> {
                        binding.progressbar.visibility = View.INVISIBLE
                        binding.recyclerFilm.visibility = View.INVISIBLE
                        binding.layoutWifi.visibility = View.VISIBLE
                        binding.textNull.visibility = View.INVISIBLE
                    }

                    State.Success -> {
                        binding.progressbar.visibility = View.INVISIBLE
                        binding.recyclerFilm.visibility = View.VISIBLE
                        binding.layoutWifi.visibility = View.INVISIBLE
                        delay(200)
                        if (binding.recyclerFilm.isEmpty()) {
                            binding.textNull.visibility = View.VISIBLE
                        } else binding.textNull.visibility = View.INVISIBLE
                    }
                }

            }
        }
    }


    private fun detailFilms(id: Int) {
        viewModule.idFilm[viewModule.counterBottomNavigation]!!.add(id)
        findNavController().navigate(R.id.action_searchFragments_to_detailFilmFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
        _binding = null
    }

}