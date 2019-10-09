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
                if (!WebAccess.offlineLog) {
                    val mainSpec = WebAccess.pediatryApi.getMainSpecialities()
                    val list = mainSpec.body()?.response ?: listOf()
                    list.forEach {
                        it.main = true
                    }
                    DatabaseAccess.database.specialityDao().saveSpeciality(list)

                    val additionalSpec = WebAccess.pediatryApi.getAdditionalSpecialities()

                    DatabaseAccess.database.specialityDao()
                        .saveSpeciality(additionalSpec.body()?.response ?: listOf())

                    val response = WebAccess.pediatryApi.getProfile()
                    DatabaseAccess.database.profileDao()
                        .saveProfile(response.body()!!.response!!.convert())
                    if (response.isSuccessful) {
                        val profile = response.body()?.response

                        val name = profile?.name
                        val lastname = profile?.lastname
                        val avatarUrl = "${profile?.avatar}"
                        withContext(Dispatchers.Main) {
                            model.name.postValue(name)
                            model.lastname.postValue(lastname)
                            model.avatarUrl.postValue(avatarUrl)
                            profileModel.city.value = profile?.city?.name
                            profileModel.name.value = profile?.name
                            profileModel.middlename.value = profile?.middlename
                            profileModel.lastname.value = profile?.lastname
                            profileModel.avatarUrl.value = "${profile?.avatar}"
                            profileModel.email.value = profile?.email
                            profileModel.phoneNumber.value = profile?.phone
                            profileModel.mainSpeciality.value = profile?.mainSpecialtyId
                            profileModel.firstAdditionalSpeciality.value =
                                profile?.additionalSpecialty1Id
                            profileModel.secondAdditionalSpeciality.value =
                                profile?.additionalSpecialty2Id
                            profileModel.additionalSpecialities =
                                additionalSpec.body()?.response ?: listOf()
                            profileModel.mainSpecialities = mainSpec.body()?.response ?: listOf()
                        }
                    }
                } else {
                    val profile = DatabaseAccess.database.profileDao().loadProfile(0)

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
                        profileModel.firstAdditionalSpeciality.value =
                            profile?.additionalSpecialty1Id
                        profileModel.secondAdditionalSpeciality.value =
                            profile?.additionalSpecialty2Id
                        profileModel.additionalSpecialities =
                            DatabaseAccess.database.specialityDao().getAdditionalSpecialities()
                        profileModel.mainSpecialities =
                            DatabaseAccess.database.specialityDao().getMainSpecialities()
                    }
                }

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
