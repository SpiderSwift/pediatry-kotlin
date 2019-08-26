package com.develop.grizzzly.pediatry.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.plusAssign

import androidx.navigation.ui.NavigationUI
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.navigation.KeepStateNavigator
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.setupWithNavController
import com.develop.grizzzly.pediatry.viewmodel.menu.MenuViewModel
import com.develop.grizzzly.pediatry.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.AppCenter
import kotlinx.android.synthetic.main.fragment_registration_speciality.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCenter.start(
            application, "924aac8e-1298-49f0-92f9-f6b48b0ad367",
            Analytics::class.java, Crashes::class.java
        )


        GlobalScope.launch {
            val response = WebAccess.pediatryApi.getBroadcasts()
            if (response.isSuccessful) {
                Log.d("TAG", response.body()?.response.toString())
            } else {
                Log.d("TAG", response.errorBody()?.string())
            }
        }

        setContentView(R.layout.activity_main)

        Log.d("TAG", "TOKEN ${WebAccess.token}")

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        val model = ViewModelProviders.of(this).get(MenuViewModel::class.java)

        val profileModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        GlobalScope.launch {
            val mainSpec = WebAccess.pediatryApi.getMainSpecialities()
            val additionalSpec = WebAccess.pediatryApi.getAdditionalSpecialities()
            val response = WebAccess.pediatryApi.getProfile()
            if (response.isSuccessful) {
                val profile = response.body()?.response


                val name = profile?.name
                val lastname = profile?.lastname
                val avatarUrl = "${profile?.avatar}"
                withContext(Dispatchers.Main) {
                    model.name.postValue(name)
                    model.lastname.postValue(lastname)
                    model.avatarUrl.postValue(avatarUrl)
                    profileModel.city.value = profile?.city
                    profileModel.name.value = profile?.name
                    profileModel.middlename.value = profile?.middlename
                    profileModel.lastname.value = profile?.lastname
                    profileModel.avatarUrl.value = "${profile?.avatar}"
                    profileModel.email.value = profile?.email
                    profileModel.phoneNumber.value = profile?.phone
                    profileModel.mainSpeciality.value = profile?.mainSpecialtyId
                    profileModel.firstAdditionalSpeciality.value = profile?.additionalSpecialty1Id
                    profileModel.secondAdditionalSpeciality.value = profile?.additionalSpecialty2Id
                    profileModel.additionalSpecialities = additionalSpec.body()?.response ?: listOf()
                    profileModel.mainSpecialities = mainSpec.body()?.response ?: listOf()
                }
            }
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        //supportActionBar?.setCustomView(R.layout.support_bar)
        //supportActionBar?.title = "The feeling of"
        //supportActionBar?.hide()
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.news_navigation, R.navigation.translations_navigation, R.navigation.menu_navigation)

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

    override fun onBackPressed() {
        bottom_nav.visibility = View.VISIBLE
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}
