package com.develop.grizzzly.pediatry.viewmodel.recovery

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.WebAccess
import kotlinx.coroutines.launch

class RecoveryViewModel : ViewModel() {
    val email = MutableLiveData<String>().apply { value = "" }

    fun onRecover(view : View) {
        viewModelScope.launch {
            val response = WebAccess.pediatryApi.restorePassword(email.value.toString())
            if (response.isSuccessful) {
                Log.d("TAG", response.body()?.string())
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_recovery_start_to_recovery_finish)
            }
        }
    }

    fun onRecoverFinish(view : View) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_recovery_finish_to_login)
    }
}