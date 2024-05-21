package com.example.final_android_lvl1.presentetion.basic.search.filter_genre_country

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.fragment.app.activityViewModels
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.databinding.MainFragmentYearFilterBinding
import com.example.final_android_lvl1.databinding.MainItemFilterYearsBinding
import com.example.final_android_lvl1.presentetion.basic.MainViewModel


class YearFilterFragment : Fragment() {
    private var _binding: MainFragmentYearFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModule: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentYearFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModule.bottomNavigation[2]!!.add(R.id.yearFilterFragment)
        viewModule.backPressed(requireActivity(), viewLifecycleOwner, R.id.yearFilterFragment)
        binding.nameWindow.back.setOnClickListener {
            viewModule.backArrow(R.id.yearFilterFragment, requireActivity())
        }
        sortDefault()
    }

    private fun sortDefault() {
        if (viewModule.filterMapInt["yearTo"] == 3000 && viewModule.filterMapInt["yearFrom"] == 1000) {
            default(2009, binding.yearsTo, false)
            default(2009, binding.yearsFrom, true)
        } else if (viewModule.filterMapInt["yearTo"] == 3000 && viewModule.filterMapInt["yearFrom"] != 1000) {
            binding.textFrom.text = viewModule.filterMapInt["yearFrom"]!!.toString()
            default(2009, binding.yearsTo, false)
            default(viewModule.filterMapInt["yearFrom"]!!, binding.yearsFrom, true)
        } else if (viewModule.filterMapInt["yearTo"] != 3000 && viewModule.filterMapInt["yearFrom"] == 1000) {
            binding.textTo.text = viewModule.filterMapInt["yearTo"]!!.toString()
            default(viewModule.filterMapInt["yearTo"]!!, binding.yearsTo, false)
            default(2009, binding.yearsFrom, true)
        } else {
            binding.textTo.text = viewModule.filterMapInt["yearTo"]!!.toString()
            binding.textFrom.text = viewModule.filterMapInt["yearFrom"]!!.toString()
            default(viewModule.filterMapInt["yearTo"]!!, binding.yearsTo, false)
            default(viewModule.filterMapInt["yearFrom"]!!, binding.yearsFrom, true)
        }
        binding.buttonReady.setOnClickListener {
            viewModule.filterMapInt["yearTo"] = binding.textTo.text.toString().toInt()
            viewModule.filterMapInt["yearFrom"] = binding.textFrom.text.toString().toInt()
            Toast.makeText(
                requireContext(),
                getString(
                    R.string.period_of_years,
                    binding.textFrom.text,
                    binding.textTo.text
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.buttonClear.setOnClickListener {
            viewModule.filterMapInt["yearTo"] = 3000
            viewModule.filterMapInt["yearFrom"] = 1000
            binding.textTo.text = viewModule.filterMapInt["yearTo"].toString()
            binding.textFrom.text = viewModule.filterMapInt["yearFrom"].toString()
            default(2009, binding.yearsTo, false)
            default(2009, binding.yearsFrom, true)
            Toast.makeText(requireContext(), getString(R.string.clear_settings), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun default(year: Int, bindingYears: MainItemFilterYearsBinding, isFrom: Boolean) {
        val listCheckBox = listOf<AppCompatRadioButton>(
            bindingYears.radioOne,
            bindingYears.radioTwo,
            bindingYears.radioThree,
            bindingYears.radioFour,
            bindingYears.radioFive,
            bindingYears.radioSix,
            bindingYears.radioSeven,
            bindingYears.radioEight,
            bindingYears.radioNine,
            bindingYears.radioTen,
            bindingYears.radioEleven,
            bindingYears.radioTwelve
        )
        //2993 любая точка от которой можно отталкиваться , ну и чтобы в диапозон макета попадало
        val countMinus = (2993 - year) / 12
        var defaultYears = 2993 - (countMinus * 12) - 12
        bindingYears.rangeYears.text = getString(
            R.string.counter_period_of_years,
            defaultYears + 1,
            defaultYears + 12
        )
        for (yearsPlus in 1..12) {
            val radioButton = listCheckBox[yearsPlus - 1]
            radioButton.text = (defaultYears + yearsPlus).toString()
            if (isFrom) {
                radioButton.isChecked = radioButton.text == binding.textFrom.text
            } else radioButton.isChecked = radioButton.text == binding.textTo.text

            radioButton.setOnClickListener {
                //c
                if (isFrom) {
                    if (binding.textFrom.text != radioButton.text)
                        if (binding.textTo.text.toString().toInt() < radioButton.text.toString()
                                .toInt()
                        ) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.error_period_max_years),
                                Toast.LENGTH_SHORT
                            ).show()
                            radioButton.isChecked = false
                        } else {
                            binding.textFrom.text = radioButton.text
                            checkedButton(listCheckBox, radioButton)
                        }
                    else {
                        radioButton.isChecked = false
                        binding.textFrom.text = getString(R.string.count, 1698)
                    }
                    //до
                } else {
                    if (binding.textTo.text != radioButton.text)
                        if (binding.textFrom.text.toString().toInt() > radioButton.text.toString()
                                .toInt()
                        ) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.error_period_min_years),
                                Toast.LENGTH_SHORT
                            ).show()
                            radioButton.isChecked = false
                        } else {
                            binding.textTo.text = radioButton.text
                            checkedButton(listCheckBox, radioButton)
                        }
                    else {
                        radioButton.isChecked = false
                        binding.textTo.text = getString(R.string.count, 2105)
                    }
                }
            }
        }
        bindingYears.nextYears.setOnClickListener {
            if (defaultYears + 13 > 2100)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.max_years_film),
                    Toast.LENGTH_SHORT
                ).show()
            else default((defaultYears + 13), bindingYears, isFrom)
        }
        bindingYears.backYears.setOnClickListener {
            if (defaultYears - 1 < 1700)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.min_years_film),
                    Toast.LENGTH_SHORT
                ).show()
            else default((defaultYears - 1), bindingYears, isFrom)
        }
    }

    private fun checkedButton(list: List<AppCompatRadioButton>, radioButton: AppCompatRadioButton) {
        for (radioButtonFalse in list) {
            if (radioButton.text != radioButtonFalse.text)
                radioButtonFalse.isChecked = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}