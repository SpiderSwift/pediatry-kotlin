package com.develop.grizzzly.pediatry.viewmodel.login

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.util.md5
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val email = MutableLiveData<String>().apply { value = "" }
    val password = MutableLiveData<String>().apply { value = "" }


    fun onLogin(view : View) {
        viewModelScope.launch {
            val response = WebAccess.pediatryApi.login(email.value.toString(), password.value.toString().md5())
            if (response.isSuccessful) {
                WebAccess.token = response.body()?.response?.token ?: ""

//                delay(5000)

                val context = view.context
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }

        }
    }

}