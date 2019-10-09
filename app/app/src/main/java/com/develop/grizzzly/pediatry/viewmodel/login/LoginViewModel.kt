package com.develop.grizzzly.pediatry.viewmodel.login

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.db.model.User
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.extensions.md5
import kotlinx.coroutines.launch
import com.develop.grizzzly.pediatry.util.showToast

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    val email = MutableLiveData<String>().apply { value = "" }
    val password = MutableLiveData<String>().apply { value = "" }

    fun onLogin(view : View) {
        viewModelScope.launch {
            try {
                val response = try {
                    Log.d(TAG, "TRY 0")
                    WebAccess.pediatryApi.login(email.value.toString(), password.value.toString().md5())
                } catch (e: Exception) {
                    Log.d(TAG, "ERR 0"); throw e
                }
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200L) {
                        try {
                            Log.d(TAG, "TRY 1")
                            WebAccess.token = response.body()?.response?.token ?: ""
                        } catch (e: Exception) {
                            Log.d(TAG, "ERR 1"); throw e
                        }
                        try {
                            Log.d(TAG, "TRY 2")
                            WebAccess.id = response.body()?.response?.id ?: 0
                        } catch (e: Exception) {
                            Log.d(TAG, "ERR 2"); throw e
                        }
                        val user = try {
                            Log.d(TAG, "TRY 3")
                            User(0, email.value, password.value.toString().md5())
                        } catch (e: Exception) {
                            Log.d(TAG, "ERR 3"); throw e
                        }
                        try {
                            Log.d(TAG, "TRY 4")
                            DatabaseAccess.database.userDao().saveUser(user)
                        } catch (e: Exception) {
                            Log.d(TAG, "ERR 4"); throw e
                        }
                        val profile = try {
                            Log.d(TAG, "TRY 5")
                            WebAccess.pediatryApi.getProfile()
                        } catch (e: Exception) {
                            Log.d(TAG, "ERR 5"); throw e
                        }
                        if (profile.isSuccessful) {
                            val profileBody = try {
                                Log.d(TAG, "TRY 6")
                                profile.body()?.response
                            } catch (e: Exception) {
                                Log.d(TAG, "ERR 6"); throw e
                            }
                            try {
                                Log.d(TAG, "TRY 7")
                                DatabaseAccess.database.profileDao().saveProfile(profileBody!!.convert())
                            } catch (e: Exception) {
                                Log.d(TAG, "ERR 7"); throw e
                            }
                        }
                        val context = view.context
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    } else {
                        showToast(view.context, R.layout.custom_toast, "Неверный email или пароль2")
                    }
                } else {
                    showToast(view.context, R.layout.custom_toast, "Неверный email или пароль")
                }
            } catch (e : Exception) {
                e.printStackTrace()
                showToast(view.context, R.layout.custom_toast, "Не удается подключиться к серверу")
            }

        }
    }

    fun onRegister(view : View) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_login_to_registration)
    }

    fun onRecover(view: View) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_login_to_recovery)
    }


}