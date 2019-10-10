package com.develop.grizzzly.pediatry.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "START ACTIVITY"

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        AppCenter.start(
            application, "b9feccee-76bf-402e-a033-d1c45613c559",
            Analytics::class.java, Crashes::class.java
        )
        GlobalScope.launch {
            val user = DatabaseAccess.database.userDao().findUser(0)
            Log.d(TAG, user.toString())
            try {
                val adsUrl = WebAccess.pediatryApi.getAdsUrl()
                val adEndpoint = "/api/"
                if(adsUrl.isSuccessful){
                    try {
                        WebAccess.adUrl = "${adsUrl.body()?.response?.url.toString()}$adEndpoint"
                        val adsResult = WebAccess.adApi.getAds()
                        if (adsResult.isSuccessful) {
                            val ads = adsResult.body()?.ads ?: listOf()
                            ads.forEach { it.image_url = "${WebAccess.adUrl}${it.image_url}" }
                            DatabaseAccess.database.adDao().saveAds(ads)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                val loginResult = WebAccess.pediatryApi.login(user?.email, user?.password)
                delay(1500)
                if (loginResult.isSuccessful) {
                    if (loginResult.body()?.status != 200L) {
                        val navController = nav_host_fragment.findNavController()
                        navController.navigate(R.id.action_start_to_login)
                    } else {
                        WebAccess.id = loginResult.body()?.response?.id ?: 0
                        WebAccess.token = loginResult.body()?.response?.token ?: ""
                        val intent = Intent(baseContext, MainActivity::class.java)
                        WebAccess.offlineLog = false
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    val navController = nav_host_fragment.findNavController()
                    navController.navigate(R.id.action_start_to_login)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                if (user != null) {
                    val intent = Intent(baseContext, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    try {
                        val navController = nav_host_fragment.findNavController()
                        navController.navigate(R.id.action_start_to_login)
                    } catch (e: Exception) {
                        e.printStackTrace()  // crashed on monkey test
                    }
                }
            }
        }
    }

    override fun onStart() {
        supportActionBar?.hide()
        super.onStart()
    }
}
