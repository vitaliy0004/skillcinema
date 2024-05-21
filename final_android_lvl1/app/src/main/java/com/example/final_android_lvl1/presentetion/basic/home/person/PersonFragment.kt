package com.example.final_android_lvl1.presentetion.basic.home.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.data.retrofit.dto.detail.person.PersonDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainFragmentPersonBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.home.AdapterFilmPremieres
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PersonFragment : Fragment() {
    @Inject
    lateinit var factory: PersonFactory
    private val personViewModule: PersonViewModule by viewModels { factory }
    private val viewModule: MainViewModel by activityViewModels()
    private var _binding: MainFragmentPersonBinding? = null
    private val binding get() = _binding!!
    private lateinit var listFilm: List<ListFilmDto>
    lateinit var person: PersonDto
    private val profileViewModule: ProfileViewModule by viewModels()
    private var transitionJob: Job? = null
    private var fragmentJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentPersonBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModule.isBack)
            viewModule.bottomNavigation[viewModule.counterBottomNavigation]!!.add(R.id.personFragment)
        viewModule.isBack = false
        viewModule.backPressed(requireActivity(), viewLifecycleOwner, R.id.personFragment)
        personViewModule.isChecked = true
        binding.itemPerson.counter.text = getString(R.string.to_the_list)
        binding.itemPerson.bestCollection.collectionText.text = getString(R.string.the_best)
        binding.itemPerson.back.setOnClickListener {
            viewModule.backArrow(R.id.personFragment, requireActivity())
        }
        binding.itemPerson.layoutCounter.setOnClickListener {
            transitionIsOnline(1)
        }
        binding.itemPerson.bestCollection.all.setOnClickListener {
            viewModule.nameCollection[viewModule.counterBottomNavigation]!!.add(getString(R.string.the_best))
            transitionIsOnline(2)
        }
        if (viewModule.listPerson[viewModule.idPerson[viewModule.counterBottomNavigation]!!.last()] == null) {
            isOnline()
        } else {
            person =
                viewModule.listPerson[viewModule.idPerson[viewModule.counterBottomNavigation]!!.last()]!!
            editBinding()
        }
    }

    private fun shortList(): List<ListFilmDto> {
        val list = mutableListOf<ListFilmDto>()
        var counter = 1
        viewModule.listFilm[viewModule.counterBottomNavigation]!!.last().forEach {
            if (counter < 10) list.add(it)
            counter += 1
        }
        return list
    }

    private fun editBinding() {
        listFilm = viewModule.listFilm[viewModule.counterBottomNavigation]!!.last()
        val adapter = AdapterFilmPremieres(
            profileViewModule,
            shortList(),
            viewModule,
            { Unit },
            { id -> detailFilms(id) },
            { listFilm -> profileViewModule.addFim(listFilm, 1) },
            { listFilm -> profileViewModule.removeFilm(listFilm, 1) }
        )
        binding.itemPerson.bestCollection.recyclerView.adapter = adapter
        if (person.films.size < 11) binding.itemPerson.bestCollection.all.visibility =
            View.INVISIBLE

        with(binding.itemPerson) {
            counterFilm.text = getString(R.string.counter_film, person.films.size)
            Glide.with(photoPerson)
                .load(person.posterUrl)
                .into(photoPerson)
            name.text = if (person.nameRu == null) person.nameEn
            else person.nameRu
            worker.text = person.profession
        }
    }

    private fun navigate(navigation: Int) {
        transitionJob = lifecycleScope.launch {
            personViewModule.bestFilm(person.films, viewModule, 2)
            if (personViewModule.isChecked) {
                if (navigation == 1) {
                    findNavController().navigate(R.id.action_personFragment_to_personProfessionFragment)
                } else {
                    findNavController().navigate(R.id.action_personFragment_to_collectionFilm)
                }
            }
        }

    }

    private fun transitionIsOnline(navigation: Int) {
        binding.layoutItemPerson.visibility = View.INVISIBLE
        if (viewModule.isOnline(requireContext())) {
            binding.layoutItemPerson.visibility = View.INVISIBLE
            binding.progressbar.visibility = View.VISIBLE
            binding.layoutWifi.visibility = View.INVISIBLE
            navigate(navigation)
        } else {
            try {
                navigate(navigation)
            } catch (e: Exception) {
                binding.layoutItemPerson.visibility = View.INVISIBLE
                binding.progressbar.visibility = View.INVISIBLE
                binding.layoutWifi.visibility = View.VISIBLE
            }
        }
        binding.wifi.textView.setOnClickListener {
            transitionIsOnline(navigation)
        }

    }

    private fun isOnline() {
        fragmentJob = lifecycleScope.launch {
            if (viewModule.isOnline(requireContext())) {
                binding.layoutItemPerson.visibility = View.INVISIBLE
                binding.progressbar.visibility = View.VISIBLE
                binding.layoutWifi.visibility = View.INVISIBLE
                binding.itemPerson.back.visibility = View.VISIBLE
                personViewModule.person(viewModule.idPerson[viewModule.counterBottomNavigation]!!.last())
                person = personViewModule.person
                viewModule.listPerson[viewModule.idPerson[viewModule.counterBottomNavigation]!!.last()] =
                    person

                personViewModule.bestFilm(person.films, viewModule, 1)
                editBinding()

                binding.layoutItemPerson.visibility = View.VISIBLE
                binding.progressbar.visibility = View.INVISIBLE
            } else {
                binding.layoutItemPerson.visibility = View.INVISIBLE
                binding.progressbar.visibility = View.INVISIBLE
                binding.itemPerson.back.visibility = View.VISIBLE
                binding.layoutWifi.visibility = View.VISIBLE
            }
        }
        binding.wifi.textView.setOnClickListener {
            isOnline()
        }
    }

    private fun detailFilms(id: Int) {
        viewModule.idFilm[viewModule.counterBottomNavigation]!!.add(id)
        findNavController().navigate(R.id.action_personFragment_to_detailFilmFragment)
    }

    override fun onStop() {
        super.onStop()

        if (transitionJob != null) {
            personViewModule.isChecked = false
            transitionJob!!.cancel()
        }
        if (fragmentJob != null) fragmentJob!!.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}