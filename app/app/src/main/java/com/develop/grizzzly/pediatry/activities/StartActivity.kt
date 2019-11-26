package com.develop.grizzzly.pediatry.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
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
            WebAccess.tryLoginWithDb()  // FIXME: it most be login first
            try {
                DatabaseAccess.database.questionDao()
                    .saveQuestions(WebAccess.pediatryApi.getQuestions(
                        (DatabaseAccess.database.questionDao().getMaxTsLastChange() ?: 0).toString()
                    ).body()?.response!!.map { it.convert() })
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                val adsUrlResult = WebAccess.pediatryApi.getAdsUrl()
                if (adsUrlResult.isSuccessful) {
                    WebAccess.adsUrl = adsUrlResult.body()?.response?.url.toString()
                    WebAccess.adsApiUrl = "${WebAccess.adsUrl}${WebAccess.adsApiEndpoint}"
                    val adsResult = WebAccess.adsApi.getAds()
                    if (adsResult.isSuccessful) {
                        val ads = adsResult.body()?.ads ?: listOf()
                        ads.forEach { it.image_url = "${WebAccess.adsUrl}${it.image_url}" }
                        DatabaseAccess.database.adDao().saveAds(ads)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            login() // FIXME: it most be login first
        }
    }

    private suspend fun login() {
        val user = DatabaseAccess.database.userDao().findUser(0)
        if (user != null) {
            try {
                val loginResult = WebAccess.pediatryApi.login(user.email, user.password)
                delay(1500)
                if (loginResult.isSuccessful)
                    WebAccess.token(loginResult.body()?.response)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                val intent = Intent(baseContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        } else {
            nav_host_fragment.findNavController()
                .navigateNoExcept(R.id.action_start_to_login)
        }
    }

    override fun onStart() {
        supportActionBar?.hide()
        super.onStart()
    }
}