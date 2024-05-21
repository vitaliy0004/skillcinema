package com.example.final_android_lvl1.presentetion.greetings

import com.example.final_android_lvl1.presentetion.basic.HomeActivity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.presentetion.ConstantString
import com.example.final_android_lvl1.presentetion.greetings.fragment.NavGravActivity


class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        // window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.greetings_activity_main)

        prefs = getSharedPreferences(ConstantString.CONTROL, MODE_PRIVATE)
        if (prefs.getBoolean(ConstantString.CONTROL, true)) goGreetings()
        else goToMain()
    }

    private fun goToMain() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goGreetings() {
        val intent = Intent(this, NavGravActivity::class.java)
        startActivity(intent)
        finish()
    }
}