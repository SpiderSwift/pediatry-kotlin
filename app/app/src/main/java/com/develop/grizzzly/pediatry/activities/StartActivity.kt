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
import com.develop.grizzzly.pediatry.network.model.Answer
import com.develop.grizzzly.pediatry.network.model.Question
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
        // val listQuestion = ArrayList<Question>()
        //  listQuestion.add(Question(0,1,"2","5","6",7,8))
        GlobalScope.launch {
            val list = mutableListOf<Answer>()
            list.add(Answer(0, "1"))
            list.add(Answer(1, "2"))
            list.add(Answer(2, "3"))
            list.add(Answer(3, "4"))
            DatabaseAccess.database.questionDao().saveQuestion(
                Question(
                    2,
                    0,
                    "tags",
                    list,
                    "Вопрос",
                    "https://edu-pediatrics.com/storage/news/188/Nestle_Ukraintsev3_360x250px.jpg",
                    1,
                    0
                )
            )
            DatabaseAccess.database.questionDao().saveQuestion(
                Question(
                    3,
                    0,
                    "tags",
                    list,
                    "Вопрос 2",
                    "https://edu-pediatrics.com/storage/news/188/Nestle_Ukraintsev3_360x250px.jpg",
                    1,
                    0
                )
            )
            //val list = DatabaseAccess.database.questionDao().getQuestions()
            //Log.println(Log.ASSERT, "msg: ", list[0].toString())
            try {
                val adsUrl = WebAccess.pediatryApi.getAdsUrl()
                if (adsUrl.isSuccessful) {
                    WebAccess.adsUrl = adsUrl.body()?.response?.url.toString()
                    WebAccess.adsApiUrl = "${WebAccess.adsUrl}${WebAccess.adsApiEndpoint}"
                    val adsResult = WebAccess.adsApi.getAds()
                    if (adsResult.isSuccessful) {
                        val ads = adsResult.body()?.ads ?: listOf()
                        ads.forEach { it.image_url = "${WebAccess.adsUrl}${it.image_url}" }
                        DatabaseAccess.database.adDao().saveAds(ads)
                    }
                }
            } catch (e: Exception) {

            }
            val user = DatabaseAccess.database.userDao().findUser(0)
            Log.d(TAG, "user: ${user.toString()}")
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
    }

    override fun onStart() {
        supportActionBar?.hide()
        super.onStart()
    }

}
