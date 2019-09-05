package com.develop.grizzzly.pediatry.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
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
import java.lang.Exception

private const val TAG = "START ACTIVITY"

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Log.e("Crated", "asdsd")
        AppCenter.start(
            application, "924aac8e-1298-49f0-92f9-f6b48b0ad367",
            Analytics::class.java, Crashes::class.java
        )

        //TODO: надо предусмотреть отсутсвие подключения к сети в каждом запросе
        if (!verifyAvailableNetwork(this)) {
            Toast.makeText(
                this,
                "Нет подключения к интернету. Возобновите подключение и перезапустите приложение.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        GlobalScope.launch {

            val user = DatabaseAccess.database.userDao().findUser(0)
            Log.d(TAG, user.toString())
            val response = WebAccess.pediatryApi.login(user?.email, user?.password)
            delay(1500)
            if (response.isSuccessful) {
                if (response.body()?.status != 200L) {
                    val navController = nav_host_fragment.findNavController()
                    navController.navigate(R.id.action_start_to_login)
                } else {
                    Log.d(TAG, response.toString())
                    Log.d(TAG, response.body().toString())
                    WebAccess.id = response.body()?.response?.id ?: 0
                    WebAccess.token = response.body()?.response?.token ?: ""
                    val intent = Intent(baseContext, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

            } else {
                val navController = nav_host_fragment.findNavController()
                navController.navigate(R.id.action_start_to_login)
            }
        }

    }

    override fun onStart() {
        supportActionBar?.hide()
        super.onStart()
    }

    fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
