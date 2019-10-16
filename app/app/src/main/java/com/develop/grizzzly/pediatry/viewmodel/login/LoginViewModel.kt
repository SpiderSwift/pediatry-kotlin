package com.develop.grizzzly.pediatry.viewmodel.login

import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.db.model.User
import com.develop.grizzzly.pediatry.extensions.md5
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.util.showToast
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    val email = MutableLiveData<String>().apply { value = "" }
    val password = MutableLiveData<String>().apply { value = "" }

    fun onLogin(view: View) {
        viewModelScope.launch {
            try {
                val passwordHash = password.value.toString().md5()
                val loginResult = WebAccess.pediatryApi.login(email.value.toString(), passwordHash)
                if (loginResult.isSuccessful) {
                    WebAccess.userToken = loginResult.body()?.response?.token ?: ""
                    WebAccess.userId = loginResult.body()?.response?.id ?: 0
                    val user = User(0, email.value, passwordHash)
                    DatabaseAccess.database.userDao().saveUser(user)
                    val profileResult = WebAccess.pediatryApi.getProfile()
                    if (profileResult.isSuccessful) {
                        val profile = profileResult.body()?.response!!.convert()
                        DatabaseAccess.database.profileDao().saveProfile(profile)
                    }
                    val context = view.context
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                } else {
                    showToast(view.context, R.layout.custom_toast, "Неверный email или пароль ${loginResult.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showToast(view.context, R.layout.custom_toast, "Не удается подключиться к серверу")
            }
        }
    }

    fun onRegister(view: View) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_login_to_registration)
    }

    fun onRecover(view: View) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_login_to_recovery)
    }

}