package com.develop.grizzzly.pediatry.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Speciality
import com.develop.grizzzly.pediatry.setupWithNavController
import com.develop.grizzzly.pediatry.viewmodel.menu.MenuViewModel
import com.develop.grizzzly.pediatry.viewmodel.profile.ProfileViewModel
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
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

        AppCenter.start(
            application, "b9feccee-76bf-402e-a033-d1c45613c559",
            Analytics::class.java, Crashes::class.java
        )

        setContentView(R.layout.activity_main)

        Log.d(TAG, "TOKEN ${WebAccess.token}")

        if (savedInstanceState == null)
            setupBottomNavigationBar()

        val model = ViewModelProviders.of(this).get(MenuViewModel::class.java)

        val profileModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        GlobalScope.launch {
            try {

                val mainSpecsResult = WebAccess.pediatryApi.getMainSpecs()
                val mainSpecs = if (mainSpecsResult.isSuccessful) {
                    mainSpecsResult.body()?.response.orEmpty()
                } else {
                    DatabaseAccess.database.specialityDao().getMainSpecs()
                }

                val extraSpecsResult = WebAccess.pediatryApi.getExtraSpecs()
                val extraSpecs = if (extraSpecsResult.isSuccessful) {
                    extraSpecsResult.body()?.response.orEmpty()
                } else {
                    DatabaseAccess.database.specialityDao().getExtraSpecs()
                }

                val profResult = WebAccess.pediatryApi.getProfile()
                val profile = if (profResult.isSuccessful) {
                    profResult.body()?.response?.convert()
                } else {
                    DatabaseAccess.database.profileDao().loadProfile(0)
                }

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
                    profileModel.phoneNumber.value = profile?.phone
                    profileModel.mainSpec.value = profile?.mainSpecialtyId
                    profileModel.extraSpec1.value = profile?.additionalSpecialty1Id
                    profileModel.extraSpec2.value = profile?.additionalSpecialty2Id
                }
//
//
//                if (!WebAccess.offlineLog) {
//                    val mainSpecResult = WebAccess.pediatryApi.getMainSpecs()
//                    val mainSpecs = mainSpecResult.body()?.response ?: listOf()
//                    mainSpecs.forEach { it.main = true }
//                    DatabaseAccess.database.specialityDao().saveSpeciality(mainSpecs)
//
//                    val extraSpecResult = WebAccess.pediatryApi.getExtraSpecs()
//                    val extraSpecs = mainSpecResult.body()?.response ?: listOf()
//                    DatabaseAccess.database.specialityDao().saveSpeciality(extraSpecs)
//
//                    val profileResult = WebAccess.pediatryApi.getProfile()
//                    val profile = profileResult.body()!!.response!!.convert()
//                    DatabaseAccess.database.profileDao().saveProfile(profile)
//
//                    withContext(Dispatchers.Main) {
//                        if (mainSpecResult.isSuccessful)
//                            profileModel.mainSpecs = mainSpecs
//                        if (extraSpecResult.isSuccessful)
//                            profileModel.extraSpecs = extraSpecs
//                        if (profileResult.isSuccessful) {
//                            model.name.postValue(profile.name)
//                            model.lastname.postValue(profile.lastname)
//                            model.avatarUrl.postValue(profile.avatar)
//                            profileModel.city.value = profile.city
//                            profileModel.name.value = profile.name
//                            profileModel.middlename.value = profile.middlename
//                            profileModel.lastname.value = profile.lastname
//                            profileModel.avatarUrl.value = profile.avatar
//                            profileModel.email.value = profile.email
//                            profileModel.phoneNumber.value = profile.phone
//                            profileModel.mainSpec.value = profile.mainSpecialtyId
//                            profileModel.extraSpec1.value = profile.additionalSpecialty1Id
//                            profileModel.extraSpec2.value = profile.additionalSpecialty2Id
//                        }
//                    }
//                } else {
//                    val mainSpecs = DatabaseAccess.database.specialityDao().getMainSpecs()
//                    val extraSpecs = DatabaseAccess.database.specialityDao().getExtraSpecs()
//                    val profile = DatabaseAccess.database.profileDao().loadProfile(0)
//                    withContext(Dispatchers.Main) {
//                        model.name.postValue(profile?.name)
//                        model.lastname.postValue(profile?.lastname)
//                        model.avatarUrl.postValue("${profile?.avatar}")
//                        profileModel.city.value = profile?.city
//                        profileModel.name.value = profile?.name
//                        profileModel.middlename.value = profile?.middlename
//                        profileModel.lastname.value = profile?.lastname
//                        profileModel.avatarUrl.value = profile?.avatar
//                        profileModel.email.value = profile?.email
//                        profileModel.phoneNumber.value = profile?.phone
//                        profileModel.mainSpec.value = profile?.mainSpecialtyId
//                        profileModel.extraSpec1.value = profile?.additionalSpecialty1Id
//                        profileModel.extraSpec2.value = profile?.additionalSpecialty2Id
//                        profileModel.extraSpecs = extraSpecs
//                        profileModel.mainSpecs = mainSpecs
//                    }
//                }

            } catch (e: Exception) {

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
        bottom_nav.visibility = View.VISIBLE
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}
