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
        GlobalScope.launch {

            //Todo delete {
            val listAnswers = mutableListOf<Answer>()
            listAnswers.add(Answer(0, "1"))
            listAnswers.add(Answer(1, "2"))
            listAnswers.add(Answer(2, "3"))
            listAnswers.add(Answer(3, "4"))

            val listInts = mutableListOf<Int>()
            listInts.add(666)
            listInts.add(999)

            val listCorrectAnswersCombo = mutableListOf<Int>()
            listCorrectAnswersCombo.add(0)
            listCorrectAnswersCombo.add(1)

            DatabaseAccess.database.questionDao().saveQuestion(
                Question(
                    id = 0,
                    tsLastChange = 5,
                    tags = listInts,
                    answers = listAnswers,
                    text = "Вопрос 1",
                    imageUrl = "https://edu-pediatrics.com/storage/news/188/Nestle_Ukraintsev3_360x250px.jpg",
                    correctAnswersCombo = listCorrectAnswersCombo,
                    hintAnswerCount = 0
                )
            )
            DatabaseAccess.database.questionDao().saveQuestion(
                Question(
                    id = 1,
                    tsLastChange = 5,
                    tags = listInts,
                    answers = listAnswers,
                    text = "Вопрос 2",
                    imageUrl = "https://edu-pediatrics.com/storage/news/186/360х250Kazan.jpg",
                    correctAnswersCombo = listCorrectAnswersCombo,
                    hintAnswerCount = 0
                )
            )
            //Todo delete }

            try {
                val adsUrl = WebAccess.pediatryApi.getAdsUrl()
                if (adsUrl.isSuccessful) {
                    WebAccess.adsUrl = adsUrl.body()?.response?.url.toString()
                    WebAccess.adsApiUrl = "${WebAccess.adsUrl}${WebAccess.adsApiEndpoint}"
                    val adsResult = WebAccess.adsApi.getAds()
                    if  (adsResult.isSuccessful) {
                        val ads = adsResult.body()?.ads ?: listOf()
                        ads.forEach { it.image_url = "${WebAccess.adsUrl}${it.image_url}" }
                        DatabaseAccess.database.adDao().saveAds(ads)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
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