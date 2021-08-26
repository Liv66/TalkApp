package com.example.talkapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

//    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var actionBar: ActionBar?

        actionBar = supportActionBar
        actionBar?.hide()

//        val host: NavHostFragment = supportFragmentMaㅁnager
//            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment

//        val navController = host.navController

//        setupBottomNavMenu(navController)

    }

//    private fun setupBottomNavMenu(navController: NavController) {
//        bottomNav = findViewById(R.id.bottom_nav_view)
//        bottomNav?.setupWithNavController(navController)
//    }
}
