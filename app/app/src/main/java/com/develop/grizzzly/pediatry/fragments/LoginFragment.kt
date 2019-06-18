package com.develop.grizzzly.pediatry.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*

import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.network.WebAccess
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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

        btnLogin.setOnClickListener {

//            GlobalScope.launch {
//
//                val login = WebAccess.pediatryApi.login("", "")
//                Log.d("TAG", login.toString())
//            }
            val intent = Intent(activity?.baseContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        //navController.navigate(R.id.registration_start)
        super.onViewCreated(view, savedInstanceState)
    }
}


