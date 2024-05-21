package com.example.final_android_lvl1.presentetion.basic.home.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.data.retrofit.dto.detail.person.PersonDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainFragmentPersonProfessionBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonProfessionFragment : Fragment() {
    private var _binding: MainFragmentPersonProfessionBinding? = null
    private val binding get() = _binding!!
    private lateinit var person: PersonDto
    private val viewModule: MainViewModel by activityViewModels()
    private val professionViewModule: PersonProfessionViewModule by viewModels()
    private lateinit var adapterPersonFilm: AdapterPersonFilm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentPersonProfessionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModule.isBack)
            viewModule.bottomNavigation[viewModule.counterBottomNavigation]!!.add(R.id.personProfessionFragment)
        viewModule.isBack = false
        viewModule.backPressed(requireActivity(), viewLifecycleOwner, R.id.personProfessionFragment)
        binding.collection.back.setOnClickListener {
            viewModule.backArrow(R.id.personProfessionFragment, requireActivity())
        }
        person =
            viewModule.listPerson[viewModule.idPerson[viewModule.counterBottomNavigation]!!.last()]!!
        binding.name.text = if (person.nameRu == null) person.nameEn
        else person.nameRu
        binding.collection.collectionName.text = getString(R.string.filmography)
        professionViewModule.sort(person)

        visibilityAndNameChip()
        adapterPersonFilm = AdapterPersonFilm(
            formatDetail(1),
            viewModule,
            { id -> onClickDetail(id) },
        )
        binding.films.adapter = adapterPersonFilm
        binding.chipTwo.setOnClickListener { clickChip(2) }
        binding.chipOne.setOnClickListener { clickChip(1) }
        binding.chipThree.setOnClickListener { clickChip(3) }
        binding.chipTour.setOnClickListener { clickChip(4) }
        binding.chipFive.setOnClickListener { clickChip(5) }

    }

    private fun onClickDetail(id: Int) {
        viewModule.idFilm[viewModule.counterBottomNavigation]!!.add(id)
        findNavController().navigate(R.id.action_personProfessionFragment_to_detailFilmFragment)
    }

    fun formatDetail(numberList: Int): List<ListFilmDto> {
        val listIdFilm =
            professionViewModule.mapChip[professionViewModule.listCollectionName[numberList - 1]]!!
        val listFilm = mutableListOf<ListFilmDto>()
        listIdFilm.forEach {
            listFilm.add(viewModule.formatListFilm(viewModule.listDetailFilm[it]!!))
        }

        return listFilm
    }

    private fun clickChip(idChip: Int) {
        when (idChip) {
            1 -> {
                binding.chipOne.isClickable = false
                binding.chipTwo.isClickable = true
                binding.chipThree.isClickable = true
                binding.chipTour.isClickable = true
                binding.chipFive.isClickable = true
            }

            2 -> {
                binding.chipOne.isClickable = true
                binding.chipTwo.isClickable = false
                binding.chipThree.isClickable = true
                binding.chipTour.isClickable = true
                binding.chipFive.isClickable = true
            }

            3 -> {
                binding.chipOne.isClickable = true
                binding.chipTwo.isClickable = true
                binding.chipThree.isClickable = false
                binding.chipTour.isClickable = true
                binding.chipFive.isClickable = true

            }

            4 -> {
                binding.chipOne.isClickable = true
                binding.chipTwo.isClickable = true
                binding.chipThree.isClickable = true
                binding.chipTour.isClickable = false
                binding.chipFive.isClickable = true
            }

            5 -> {
                binding.chipOne.isClickable = true
                binding.chipTwo.isClickable = true
                binding.chipThree.isClickable = true
                binding.chipTour.isClickable = true
                binding.chipFive.isClickable = false
            }
        }

        adapterPersonFilm = AdapterPersonFilm(
            formatDetail(idChip),
            viewModule,
            { id -> onClickDetail(id) },
        )
        binding.films.adapter = adapterPersonFilm
    }


    private fun visibilityAndNameChip() {
        var counter = 0
        when (professionViewModule.listCollectionName.size) {
            1 -> {
                binding.chipTwo.visibility = View.GONE
                binding.chipThree.visibility = View.GONE
                binding.chipTour.visibility = View.GONE
                binding.chipFive.visibility = View.GONE
            }

            2 -> {
                binding.chipThree.visibility = View.GONE
                binding.chipTour.visibility = View.GONE
                binding.chipFive.visibility = View.GONE
            }

            3 -> {
                binding.chipTour.visibility = View.GONE
                binding.chipFive.visibility = View.GONE
            }

            4 -> binding.chipFive.visibility = View.GONE
        }
        professionViewModule.listCollectionName.forEach {
            counter += 1
            when (counter) {
                1 -> binding.chipOne.text = "$it ${professionViewModule.mapChip[it]!!.size}"
                2 -> binding.chipTwo.text = "$it ${professionViewModule.mapChip[it]!!.size}"
                3 -> binding.chipThree.text = "$it ${professionViewModule.mapChip[it]!!.size}"
                4 -> binding.chipTour.text = "$it ${professionViewModule.mapChip[it]!!.size}"
                5 -> binding.chipFive.text = "$it ${professionViewModule.mapChip[it]!!.size}"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}