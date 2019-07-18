package com.develop.grizzzly.pediatry.viewmodel.registration

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Speciality
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel()  {

    val email = MutableLiveData<String>().apply { value = "" }
    val password = MutableLiveData<String>().apply { value = "" }
    val city = MutableLiveData<String>().apply { value = "" }
    val fullname = MutableLiveData<String>().apply { value = "" }
    val mainSpeciality = MutableLiveData<Speciality>().apply { value = null }
    val firstAdditionalSpeciality = MutableLiveData<Speciality>().apply { value = null }
    val secondAdditionalSpeciality = MutableLiveData<Speciality>().apply { value = null }
    val phoneNumber = MutableLiveData<String>().apply { value = "" }
    val valid = MutableLiveData<Boolean>().apply { value = false }


    fun onRegister(view : View) {
        viewModelScope.launch {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_registration_start_to_registration_code)
        }


    }

}