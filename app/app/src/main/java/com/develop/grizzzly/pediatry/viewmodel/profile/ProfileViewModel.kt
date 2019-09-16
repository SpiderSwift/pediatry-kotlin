package com.develop.grizzzly.pediatry.viewmodel.profile

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.model.Speciality

class ProfileViewModel : ViewModel() {

    val email = MutableLiveData<String>().apply { value = "" }
    val password = MutableLiveData<String>().apply { value = "" }
    val city = MutableLiveData<String>().apply { value = "" }
    val name = MutableLiveData<String>().apply { value = "" }
    val lastname = MutableLiveData<String>().apply { value = "" }
    val middlename = MutableLiveData<String>().apply { value = "" }
    val mainSpeciality = MutableLiveData<Long>().apply { value = null }
    val firstAdditionalSpeciality = MutableLiveData<Long>().apply { value = null }
    val secondAdditionalSpeciality = MutableLiveData<Long>().apply { value = null }
    val avatarUrl = MutableLiveData<String>().apply { value = "error" }
    val newAvatar = MutableLiveData<Uri>().apply { value = null }
    val phoneNumber = MutableLiveData<String>().apply { value = "" }

    var mainSpecialities: List<Speciality> = listOf()
    var additionalSpecialities: List<Speciality> = listOf()

    fun getMainSpecialityName(): String {
        for (spec in mainSpecialities) {
            if (spec.id == mainSpeciality.value) {
                return spec.name
            }
        }
        return ""
    }

    fun getFirstAdditionalSpecialityName(): String {
        for (spec in additionalSpecialities) {
            if (spec.id == firstAdditionalSpeciality.value) {
                return spec.name
            }
        }
        return ""
    }


    fun getNumber(): String {
        return try {
            val value = phoneNumber.value!!
            if (value.substring(2, 3) == " ") {
                return value
            }
            val code = value.substring(0, 2)
            val operator = value.substring(2, 7)
            val num = value.substring(7)
            return "$code $operator $num"

        } catch (ignored: Exception) {
            ""
        }
    }

    fun getSecondAdditionalSpecialityName(): String {
        for (spec in additionalSpecialities) {
            if (spec.id == secondAdditionalSpeciality.value) {
                return spec.name
            }
        }
        return ""
    }


    fun onEdit(view: View) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_profile_to_edit)
    }

}