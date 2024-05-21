package com.example.final_android_lvl1.presentetion.basic

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.databinding.MainActivityHomeBinding
import com.example.final_android_lvl1.presentetion.ConstantString
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var prefs: SharedPreferences
    private val profileViewModel: ProfileViewModule by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences(ConstantString.CONTROL, MODE_PRIVATE)
        binding = MainActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //для создания стандартных коллекций
        if (prefs.getBoolean(ConstantString.CONTROL, true)) profileViewModel.isStandardCollections()
        prefs.edit().putBoolean(ConstantString.CONTROL, false).apply()


        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        viewModel.navController = navHost.navController
        setBottomNavigation()

    }

    private fun setBottomNavigation() {
        binding.bottomNavigationView.setupWithNavController(navController = viewModel.navController)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    if (viewModel.counterBottomNavigation != 1) {
                        viewModel.counterBottomNavigation = 1
                        viewModel.navController.navigate(viewModel.bottomNavigation[1]!!.last())
                        if (viewModel.bottomNavigation[1]!!.size > 1) viewModel.bottomNavigation[1]!!.removeLast()
                    }
                    true
                }

                R.id.searchFragment -> {
                    if (viewModel.counterBottomNavigation != 2) {
                        viewModel.counterBottomNavigation = 2
                        viewModel.navController.navigate(viewModel.bottomNavigation[2]!!.last())
                        if (viewModel.bottomNavigation[2]!!.size > 1) viewModel.bottomNavigation[2]!!.removeLast()
                    }
                    true

                }

                else -> {
                    if (viewModel.counterBottomNavigation != 3) {
                        viewModel.counterBottomNavigation = 3
                        viewModel.navController.navigate(viewModel.bottomNavigation[3]!!.last())
                        if (viewModel.bottomNavigation[3]!!.size > 1) viewModel.bottomNavigation[3]!!.removeLast()
                    }
                    true
                }
            }
        }
    }
}