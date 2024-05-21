package com.example.final_android_lvl1.presentetion.basic.home.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.databinding.MainFragmentAllPersonBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel

class AllPersonFragment : Fragment() {
    private var _binding: MainFragmentAllPersonBinding? = null
    private val binding get() = _binding!!
    private val viewModule: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentAllPersonBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModule.isBack)
            viewModule.bottomNavigation[viewModule.counterBottomNavigation]!!.add(R.id.allPersonFragment)
        viewModule.isBack = false
        viewModule.backPressed(requireActivity(), viewLifecycleOwner, R.id.allPersonFragment)
        binding.collection.progressbar.visibility = View.INVISIBLE
        binding.collection.allFilm.adapter =
            AdapterPerson(viewModule.listWorkerAndActor[viewModule.counterBottomNavigation]!!.last()) { id ->
                person(id)
            }
        binding.collection.barName.back.setOnClickListener {
            viewModule.backArrow(R.id.allPersonFragment, requireActivity())
        }
    }

    private fun person(id: Int) {
        viewModule.idPerson[viewModule.counterBottomNavigation]!!.add(id)
        findNavController().navigate(R.id.action_allPersonFragment_to_personFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}