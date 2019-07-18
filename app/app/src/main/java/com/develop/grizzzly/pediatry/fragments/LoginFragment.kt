package com.develop.grizzzly.pediatry.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*

import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.WebAccess
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController = findNavController()


        tvForgotPassword.setOnClickListener {
            navController.navigate(R.id.action_login_to_recovery)
        }


        tvRegister.setOnClickListener {
            navController.navigate(R.id.action_login_to_registration)
        }


//        GlobalScope.launch {
//            val response = WebAccess.pediatryApi.register(
//                    "artem",
//                    "pishchalov",
//                    "alexandrovich",
//                    "a.pishchalov1997@gmail.com",
//                    "Moscow",
//                    "+7(111)111-11-11",
//                    "1",
//                md5("123456789123")
//            )
//            Log.d("TAG", md5("123456789123"))
//            if (response.isSuccessful) {
//                Log.d("TAG", "Response1 ${response}")
//                Log.d("TAG", "Response ${response.body()}")
//            } else {
//                Log.d("TAG", "Response2 ${response}")
//                Log.d("TAG", "Response ${response.errorBody()?.string()}")
//            }
//
//        }


        btnLogin.setOnClickListener {

            GlobalScope.launch {

                val login = WebAccess.pediatryApi.login("a.pishchalov1997@gmail.com", md5("123456789123"))
                val token = login.body()?.response?.token
                WebAccess.token = token.toString()

                //val news = WebAccess.pediatryApi.getNews()
                //val conferences = WebAccess.pediatryApi.getConferences()
                val api = WebAccess.pediatryApi

                val news = WebAccess.pediatryApi.getBroadcasts()
//                val conf = WebAccess.pediatryApi.getConferences()
//                val specMain = WebAccess.pediatryApi.getMainSpecialities()
//                val specAdditional = WebAccess.pediatryApi.getAdditionalSpecialities()
//                val broadcasts = WebAccess.pediatryApi.getBroadcasts()
//                val archWeb = api.getArchiveWebinars()
//                val web = api.getWebinars()
//                val profile = api.getProfile()

                if (news.isSuccessful) {
                    Log.d("TAG", "SUCCESS")
                    Log.d("TAG", "NEWS ${news.body()}")
//                    Log.d("TAG", "CONF ${conf.body()}")
//                    Log.d("TAG", "SPEC MAIN ${specMain.body()?.response}")
//                    Log.d("TAG", "SPEC ADD ${specAdditional.body()?.response}")
//                    Log.d("TAG", "BROADCASTS ${broadcasts.body()}")
//                    Log.d("TAG", "ARCHIVE WEB ${archWeb.body()}")
//                    Log.d("TAG", "WEB ${web.body()}")
//                    Log.d("TAG", "PROFILE ${profile.body()}")
                }
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)

            }
//            val intent = Intent(activity?.baseContext, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
        }

        //navController.navigate(R.id.registration_start)
        super.onViewCreated(view, savedInstanceState)
    }

    fun md5(s: String): String {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest
                .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }



}


