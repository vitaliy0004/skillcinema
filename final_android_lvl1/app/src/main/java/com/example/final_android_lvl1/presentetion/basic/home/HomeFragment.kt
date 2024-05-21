package com.example.final_android_lvl1.presentetion.basic.home

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
import com.example.final_android_lvl1.data.retrofit.dto.preview.FilmDto
import com.example.final_android_lvl1.databinding.MainFragmentHomeBinding
import com.example.final_android_lvl1.databinding.MainItemHomeBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModule: MainViewModel by activityViewModels()
    private var _binding: MainFragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val profileViewModule: ProfileViewModule by viewModels()
    private lateinit var job: Job

    @Inject
    lateinit var factory: FactoryViewModelHome
    private val homeViewModel: HomeViewModel by viewModels { factory }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModule.isBack = false
        viewModule.backPressed(requireActivity(), viewLifecycleOwner, R.id.homeFragments)
        lifecycleScope.launch { isOnline() }

        binding.wifi.textView.setOnClickListener {
            lifecycleScope.launch {
                isOnline()
            }
        }
    }

    private fun isOnline() {
        job = lifecycleScope.launch {
            if (viewModule.isOnline(requireContext()) || viewModule.previewCollection[0] != null) {
                binding.wifiLayout.visibility = View.GONE
                binding.collection.visibility = View.GONE
                binding.progressbar.visibility = View.VISIBLE
                if (viewModule.countryAndGenre == null) viewModule.countryAndGenre =
                    homeViewModel.CountryAndGenre()
                homeViewModel.filterCountryAndGenre(viewModule.countryAndGenre!!)
                //1
                if (viewModule.previewCollection[0] != null) homeViewModel.listFilm =
                    viewModule.previewCollection[0]!!
                else {
                    homeViewModel.getPremiers(
                        Calendar.getInstance().get(Calendar.YEAR),
                        homeViewModel.month[Calendar.getInstance().get(Calendar.MONTH) + 1]!!,
                        1
                    )

                    //для того чтобы когда был переход на  страницу определённой колекции  было с верхуотображено имя колекции
                    viewModule.controllerCollection = 1

                    homeViewModel.twoWeeks(viewModule)

                    viewModule.previewCollection[0] = homeViewModel.listFilm
                }
                adapterFilm(
                    binding.premieres,
                    homeViewModel.nameCollection(homeViewModel.collectionName[0]),
                    viewModule.previewCollection[0]!!
                )
                //2
                if (viewModule.previewCollection[1] != null) homeViewModel.listFilm =
                    viewModule.previewCollection[1]!!
                else {
                    homeViewModel.getPopular(1)
                    viewModule.previewCollection[1] = homeViewModel.listFilm
                }
                viewModule.controllerCollection = 2
                adapterFilm(
                    binding.popular,
                    homeViewModel.nameCollection(homeViewModel.collectionName[1]),
                    viewModule.previewCollection[1]!!
                )
                //3
                if (viewModule.previewCollection[2] != null) homeViewModel.listFilm =
                    viewModule.previewCollection[2]!!
                else {
                    homeViewModel.getTopFilm(1)
                    viewModule.previewCollection[2] = homeViewModel.listFilm
                }
                viewModule.controllerCollection = 3
                adapterFilm(
                    binding.topFilm,
                    homeViewModel.nameCollection(homeViewModel.collectionName[2]),
                    viewModule.previewCollection[2]!!
                )
                //4
                viewModule.controllerCollection = 4
                if (viewModule.previewCollection[3] != null) {
                    homeViewModel.listFilm = viewModule.previewCollection[3]!!


                    homeViewModel.countrys = viewModule.countrys
                    homeViewModel.genre = viewModule.genres
                } else {
                    homeViewModel.countryAndGenre(0, 1)
                    viewModule.previewCollection[3] = homeViewModel.listFilm
                }
                adapterFilm(
                    binding.countryAndGenre,
                    homeViewModel.nameCollection(homeViewModel.nameCollectionCountryAndGenre(0)),
                    viewModule.previewCollection[3]!!
                )
                //5
                viewModule.controllerCollection = 5
                if (viewModule.previewCollection[4] != null) homeViewModel.listFilm =
                    viewModule.previewCollection[4]!!
                else {
                    homeViewModel.countryAndGenre(1, 1)
                    viewModule.previewCollection[4] = homeViewModel.listFilm
                    viewModule.countrys = homeViewModel.countrys
                    viewModule.genres = homeViewModel.genre
                }
                adapterFilm(
                    binding.countryAndGenre2,
                    homeViewModel.nameCollection(homeViewModel.nameCollectionCountryAndGenre(1)),
                    viewModule.previewCollection[4]!!
                )
                //6
                if (viewModule.previewCollection[5] != null) homeViewModel.listFilm =
                    viewModule.previewCollection[5]!!
                else {
                    homeViewModel.getMiniSeries("TV_SERIES", 1)
                    viewModule.previewCollection[5] = homeViewModel.listFilm
                }
                viewModule.controllerCollection = 6
                adapterFilm(
                    binding.miniSeries,
                    homeViewModel.nameCollection(homeViewModel.collectionName[5]),
                    viewModule.previewCollection[5]!!
                )

                binding.collection.visibility = View.VISIBLE
                binding.progressbar.visibility = View.INVISIBLE
            } else {
                binding.wifiLayout.visibility = View.VISIBLE
                binding.collection.visibility = View.INVISIBLE

            }
        }

    }

    private fun adapterFilm(item: MainItemHomeBinding, collection: String, filmDto: FilmDto) {
        val listFilm = if (filmDto.items == null) filmDto.films
        else filmDto.items
        if (listFilm.size < 20) item.all.visibility = View.INVISIBLE
        else item.all.visibility = View.VISIBLE

        val adapter = AdapterFilmPremieres(
            profileViewModule,
            listFilm,
            viewModule,
            { allCollectionFilms() },
            { filmInt -> detailFilms(filmInt) },
            { filmDto -> profileViewModule.addFim(filmDto, 1) },
            { filmDto -> profileViewModule.removeFilm(filmDto, 1) }
        )
        item.recyclerView.adapter = adapter
        item.collectionText.text = collection
        item.all.setOnClickListener {
            when (item.collectionText.text) {
                homeViewModel.collectionName[0] -> viewModule.controllerCollection = 1
                homeViewModel.collectionName[1] -> viewModule.controllerCollection = 2
                homeViewModel.collectionName[2] -> viewModule.controllerCollection = 3
                homeViewModel.collectionName[3] -> viewModule.controllerCollection = 4
                homeViewModel.collectionName[4] -> viewModule.controllerCollection = 5
                homeViewModel.collectionName[5] -> viewModule.controllerCollection = 6
            }
            viewModule.nameCollection[viewModule.counterBottomNavigation]!!
                .add(item.collectionText.text.toString())
            allCollectionFilms()
        }
    }

    private fun allCollectionFilms() {
        findNavController().navigate(R.id.action_homeFragment_to_collectionFilm)
    }

    private fun detailFilms(id: Int) {
        viewModule.idFilm[viewModule.counterBottomNavigation]!!.add(id)
        findNavController().navigate(R.id.action_homeFragments_to_detailFilmFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
        _binding = null
    }

}