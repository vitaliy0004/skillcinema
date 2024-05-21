package com.example.final_android_lvl1.presentetion.basic.home.all_collection

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.databinding.MainFragmentAllCollectionBinding
import com.example.final_android_lvl1.presentetion.ConstantString
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.home.FactoryViewModelHome
import com.example.final_android_lvl1.presentetion.basic.home.HomeViewModel
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule
import com.example.final_android_lvl1.presentetion.basic.search.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllCollectionFragment : Fragment() {
    @Inject
    lateinit var factory: FactoryViewModelHome
    private val homeViewModel: HomeViewModel by viewModels { factory }
    private var _binding: MainFragmentAllCollectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var prefrs: SharedPreferences.Editor
    private lateinit var adapterSimilar: AdapterSimilarCollection
    private val profileViewModule: ProfileViewModule by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentAllCollectionBinding.inflate(layoutInflater)
        prefrs = requireContext().getSharedPreferences(
            ConstantString.FILM_COLLECTION,
            Context.MODE_PRIVATE
        )?.edit()!!
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModel.isBack)
            viewModel.bottomNavigation[viewModel.counterBottomNavigation]!!.add(R.id.collectionFilm)
        viewModel.isBack = false
        viewModel.backPressed(requireActivity(), viewLifecycleOwner, R.id.collectionFilm)
        binding.barName.back.setOnClickListener {
            viewModel.backArrow(R.id.collectionFilm, requireActivity())
        }
        isOnline()
        binding.noWifi.textView.setOnClickListener {
            isOnline()
        }
    }

    private fun isOnline() {
        binding.barName.collectionName.text =
            viewModel.nameCollection[viewModel.counterBottomNavigation]!!.last()
        if (
            viewModel.nameCollection[viewModel.counterBottomNavigation]!!.last() == getString(R.string.similar_films)
            || viewModel.nameCollection[viewModel.counterBottomNavigation]!!.last() == getString(R.string.the_best)
            || viewModel.counterBottomNavigation == 3
        ) {
            binding.allFilm.visibility = View.VISIBLE
            binding.layoutNoWifi.visibility = View.INVISIBLE
            binding.progressbar.visibility = View.INVISIBLE
            viewModel.isOnline(requireContext())
            adapterSimilar = AdapterSimilarCollection(
                profileViewModule,
                viewModel.listFilm[viewModel.counterBottomNavigation]!!.last(),
                viewModel,
                { id -> detailFilms(id) },
                { listFilmDto -> profileViewModule.addFim(listFilmDto, 1) },
                { listFilmDto -> profileViewModule.removeFilm(listFilmDto, 1) }
            )

            binding.allFilm.adapter = adapterSimilar
        } else {
            val filmAdapter = AdapterAllCollection(
                profileViewModule,
                viewModel,
                { id -> detailFilms(id) },
                { listFilmDto -> profileViewModule.addFim(listFilmDto, 1) },
                { listFilmDto -> profileViewModule.removeFilm(listFilmDto, 1) }
            )
            homeViewModel.pagedMovies(viewModel, requireContext())
            putInt()
            binding.allFilm.adapter = filmAdapter.withLoadStateFooter(LoadFilmStateAdapter())
            homeViewModel.pagedMovies.onEach {
                filmAdapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            lifecycleScope.launch {
                homeViewModel.state.collect { state ->
                    when (state) {
                        State.Loading -> {
                            binding.allFilm.visibility = View.INVISIBLE
                            binding.layoutNoWifi.visibility = View.INVISIBLE
                            binding.progressbar.visibility = View.VISIBLE
                        }

                        State.Success -> {
                            binding.allFilm.visibility = View.VISIBLE
                            binding.layoutNoWifi.visibility = View.INVISIBLE
                            binding.progressbar.visibility = View.INVISIBLE
                        }

                        State.Error -> {
                            binding.allFilm.visibility = View.INVISIBLE
                            binding.layoutNoWifi.visibility = View.VISIBLE
                            binding.progressbar.visibility = View.INVISIBLE
                        }
                    }
                }
            }

        }
    }

    private fun detailFilms(id: Int) {
        viewModel.idFilm[viewModel.counterBottomNavigation]!!.add(id)
        findNavController().navigate(R.id.action_collectionFilm_to_detailFilmFragment)
    }

    private fun putInt() {
        prefrs.putInt(ConstantString.COUNTER_COLLECTION, viewModel.controllerCollection).apply()
        prefrs.putInt(ConstantString.COUNTRY1, viewModel.countrys[0]).apply()
        prefrs.putInt(ConstantString.COUNTRY2, viewModel.countrys[1]).apply()
        prefrs.putInt(ConstantString.GENRE1, viewModel.genres[0]).apply()
        prefrs.putInt(ConstantString.GENRE2, viewModel.genres[1]).apply()
    }

}