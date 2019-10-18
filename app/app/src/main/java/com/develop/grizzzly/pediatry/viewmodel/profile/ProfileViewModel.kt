package com.develop.grizzzly.pediatry.viewmodel.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.network.model.Speciality

private const val TAG = "PROFILE VIEW MODEL"

class ProfileViewModel : ViewModel() {

    val name = MutableLiveData<String>().apply { value = "" }
    val lastname = MutableLiveData<String>().apply { value = "" }
    val middlename = MutableLiveData<String>().apply { value = "" }
    val email = MutableLiveData<String>().apply { value = "" }
    val city = MutableLiveData<String>().apply { value = "" }
    val kladrId = MutableLiveData<String>().apply { value = "5000000123900" }
    val country = MutableLiveData<String>().apply { value = "russia" }
    val fullCity = MutableLiveData<String>().apply { value = "moscow district, " }
    val phoneNumber = MutableLiveData<String>().apply { value = "" }
    val mainSpec = MutableLiveData<Long>().apply { value = null }
    val extraSpec1 = MutableLiveData<Long>().apply { value = null }
    val extraSpec2 = MutableLiveData<Long>().apply { value = null }
    val avatarUrl = MutableLiveData<String>().apply { value = "error" }
    val newAvatar = MutableLiveData<Uri>().apply { value = null }
    val password = MutableLiveData<String>().apply { value = "" }

    var mainSpecs: List<Speciality> = listOf()
    var extraSpecs: List<Speciality> = listOf()
    var fragment: Fragment? = null

    fun getMainSpecialityName(): String {
        for (spec in mainSpecs)
            if (spec.id == mainSpec.value)
                return spec.name
        return ""
    }

    fun getFirstAdditionalSpecialityName(): String {
        for (spec in extraSpecs)
            if (spec.id == extraSpec1.value)
                return spec.name
        return ""
    }

    fun getNumber(): String {
        return try {
            val value = phoneNumber.value!!
            if (value.substring(2, 3) == " ")
                return value
            val code = value.substring(0, 2)
            val operator = value.substring(2, 7)
            val num = value.substring(7)
            return "$code $operator $num"
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun getSecondAdditionalSpecialityName(): String {
        for (spec in extraSpecs)
            if (spec.id == extraSpec2.value)
                return spec.name
        return ""
    }

    fun onEdit(view: View) {
        Navigation.findNavController(view)
            .navigateNoExcept(R.id.action_profile_to_edit)
    }

    fun onChangePhoto(view: View) {
        if (checkReadPermission() && checkWritePermission()) {
            Log.d(TAG, "has permission to read external storage")
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            fragment?.startActivityForResult(intent, 200)
        } else {
            Log.d(TAG, "no permission to read external storage")
            ActivityCompat.requestPermissions(
                fragment?.activity!!,
                Array(2) {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                200
            )
        }
    }

    fun checkReadPermission(): Boolean {
        return fragment?.activity?.applicationContext?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } == PackageManager.PERMISSION_GRANTED
    }

    fun checkWritePermission(): Boolean {
        return fragment?.activity?.applicationContext?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        } == PackageManager.PERMISSION_GRANTED
    }

}