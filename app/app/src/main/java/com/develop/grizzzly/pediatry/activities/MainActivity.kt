package com.develop.grizzzly.pediatry.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController

import androidx.navigation.ui.NavigationUI
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TAG", "TOKEN ${WebAccess.token}")

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        //supportActionBar?.hide()
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.news_navigation, R.navigation.translations_navigation, R.navigation.messages_navigation)

        val controller = bottom_nav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.bottomNavFragment,
            intent = intent
        )

        controller.observe(this, Observer { navController ->
            NavigationUI.setupActionBarWithNavController(this, navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d("MainActivity", currentNavController?.value?.toString())
        return currentNavController?.value?.navigateUp() ?: false
    }
}
