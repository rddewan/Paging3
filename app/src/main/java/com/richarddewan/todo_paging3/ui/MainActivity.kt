package com.richarddewan.todo_paging3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.richarddewan.todo_paging3.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setupWithNavController(navController)
        /*
        he set of destinations by id considered at the top level of your information hierarchy.
        The Up button will not be displayed when on these destinations.
         */
        val appBarConfiguration = AppBarConfiguration(
                setOf(R.id.nav_home,R.id.nav_flow,R.id.nav_rxjava)
        )
        /*
        By calling this method, the title in the action bar will automatically be updated when
        the destination changes
         */
        setupActionBarWithNavController(navController,appBarConfiguration)
    }
}