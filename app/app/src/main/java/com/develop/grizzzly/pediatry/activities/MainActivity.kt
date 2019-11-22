package com.develop.grizzzly.pediatry.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.extensions.setupWithNavController
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.viewmodel.menu.MenuViewModel
import com.develop.grizzzly.pediatry.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MAIN ACTIVITY"

class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            setupBottomNavigationBar()
        val model = ViewModelProvider(this).get(MenuViewModel::class.java)
        val profileModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        GlobalScope.launch {
            try {
                val mainSpecsResult = WebAccess.pediatryApi.getMainSpecs()
                val mainSpecs =
                    if (mainSpecsResult.isSuccessful) mainSpecsResult.body()?.response.orEmpty()
                    else DatabaseAccess.database.specialityDao().getMainSpecs()
                val extraSpecsResult = WebAccess.pediatryApi.getExtraSpecs()
                val extraSpecs =
                    if (extraSpecsResult.isSuccessful) extraSpecsResult.body()?.response.orEmpty()
                    else DatabaseAccess.database.specialityDao().getExtraSpecs()
                val profileResult = WebAccess.pediatryApi.getProfile()
                val profile =
                    if (profileResult.isSuccessful) profileResult.body()?.response?.convert()
                    else DatabaseAccess.database.profileDao().loadProfile(0)
                withContext(Dispatchers.Main) {
                    profileModel.mainSpecs = mainSpecs
                    profileModel.extraSpecs = extraSpecs
                    model.name.postValue(profile?.name)
                    model.lastname.postValue(profile?.lastname)
                    model.avatarUrl.postValue("${profile?.avatar}")
                    profileModel.city.value = profile?.city
                    profileModel.name.value = profile?.name
                    profileModel.middlename.value = profile?.middlename
                    profileModel.lastname.value = profile?.lastname
                    profileModel.avatarUrl.value = profile?.avatar
                    profileModel.email.value = profile?.email
                    profileModel.phone.value = profile?.phone
                    profileModel.mainSpec.value = profile?.mainSpecialtyId
                    profileModel.extraSpec1.value = profile?.additionalSpecialty1Id
                    profileModel.extraSpec2.value = profile?.additionalSpecialty2Id
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState ?: Bundle.EMPTY)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.news_navigation,
            R.navigation.translations_navigation,
            R.navigation.menu_navigation
        )
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
        //bottom_nav.visibility = View.VISIBLE todo так ли должно быть? || оно всё портит :\ || испавил, добавив в некоторых местках показ бара
        if (this.resources.configuration.orientation == 2) {
            Log.println(Log.ASSERT, "msg", "SCREEN_ORIENTATION")
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

}
