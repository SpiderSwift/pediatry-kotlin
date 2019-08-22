package com.develop.grizzzly.pediatry.viewmodel.registration

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.develop.grizzzly.pediatry.R
import kotlinx.coroutines.launch

import com.develop.grizzzly.pediatry.fragments.RegistrationSpecialityFragmentDirections
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Speciality
import com.develop.grizzzly.pediatry.util.*

import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.MediaType
import java.io.File
import java.util.*




class RegistrationViewModel : ViewModel()  {

    val email = MutableLiveData<String>().apply { value = "" }
    val password = MutableLiveData<String>().apply { value = "" }
    val city = MutableLiveData<String>().apply { value = "" }
    val fullname = MutableLiveData<String>().apply { value = "" }
    val mainSpeciality = MutableLiveData<Speciality>().apply { value = null }
    val firstAdditionalSpeciality = MutableLiveData<Speciality>().apply { value = null }
    val secondAdditionalSpeciality = MutableLiveData<Speciality>().apply { value = null }
    val phoneNumber = MutableLiveData<String>().apply { value = "" }
    val startValid = MutableLiveData<Boolean>().apply { value = false }
    val infoValid = MutableLiveData<Boolean>().apply { value = false }
    val specialityValid = MutableLiveData<Boolean>().apply { value = false }
    val imageUrl = MutableLiveData<Uri>().apply { value = null }

    var fragment : Fragment? = null


    fun onRegistrationStart(view : View) {
        viewModelScope.launch {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_registration_start_to_registration_info)
        }
    }

    fun onRegistrationInfo(view : View) {
        viewModelScope.launch {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_registration_info_to_registration_speciality)
        }
    }

    fun onRegistration(view : View) {
        viewModelScope.launch {

            val fullname = fullname.value?.split(" ")

            val file = File(getPath(view.context, imageUrl.value!!))
            val requestFile = RequestBody.create(MediaType.parse(fragment!!.activity!!.contentResolver.getType(imageUrl.value!!)!!), file)

            val textType = MediaType.parse("text/plain")

            val response = WebAccess.pediatryApi.register(
                RequestBody.create(textType, fullname!![1]),
                RequestBody.create(textType, fullname[0]),
                RequestBody.create(textType, fullname[2]),
                RequestBody.create(textType, email.value!!),
                RequestBody.create(textType, city.value!!),
                RequestBody.create(textType, phoneNumber.value!!),
                RequestBody.create(textType, mainSpeciality.value!!.id.toString()),
                RequestBody.create(textType, firstAdditionalSpeciality.value!!.id.toString()),
                RequestBody.create(textType, secondAdditionalSpeciality.value!!.id.toString()),
                RequestBody.create(textType, password.value!!.md5()),
                requestFile
            )

            if (response.isSuccessful) {
                Log.d("TAG", response.toString())
                Log.d("TAG", response.body()?.string())
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_registration_speciality_to_registration_finish)
            } else {
                Log.d("TAG", response.toString())
                Log.d("TAG", response.errorBody()?.string())
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_registration_speciality_to_registration_finish_error)
                //Toast.makeText(view.context, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
            }


        }
    }

    fun onChangePhoto(view : View) {

        if (fragment?.activity?.applicationContext?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) } == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "has perm")
            val i = Intent(Intent.ACTION_OPEN_DOCUMENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "image/*"
            fragment?.startActivityForResult(i, 200)
        } else {
            Log.d("TAG", "no perm")
            ActivityCompat.requestPermissions(fragment?.activity!!,
                Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE},
                200)
        }

    }

//    fun onMainSpeciality(view : View) {
//        val navController = Navigation.findNavController(view)
//        val toSpecialitiesList =
//            RegistrationSpecialityFragmentDirections.actionRegistrationSpecialityToSpecialitiesList()
//        toSpecialitiesList.specialityType = 0
//        navController.navigate(toSpecialitiesList)
//    }
//
//    fun onFirstAdditionalSpeciality(view : View) {
//        val navController = Navigation.findNavController(view)
//        val toSpecialitiesList =
//            RegistrationSpecialityFragmentDirections.actionRegistrationSpecialityToSpecialitiesList()
//        toSpecialitiesList.specialityType = 1
//        navController.navigate(toSpecialitiesList)
//    }
//
//    fun onSecondAdditionalSpeciality(view : View) {
//        val navController = Navigation.findNavController(view)
//        val toSpecialitiesList =
//            RegistrationSpecialityFragmentDirections.actionRegistrationSpecialityToSpecialitiesList()
//        toSpecialitiesList.specialityType = 2
//        navController.navigate(toSpecialitiesList)
//    }


    fun isStartValid() : Boolean {
        return (email.value?.isEmail() ?: false) and (password.value?.isNotEmpty() ?: false)
    }

    fun isInfoValid() : Boolean {
        return (fullname.value?.split(" ")?.size == 3) and (phoneNumber.value?.isPhoneNumber() ?: false) and (city.value?.isNotEmpty() ?: false)
    }

    fun isSpecialityValid() : Boolean {
        return (mainSpeciality.value != null)
    }

    fun clearStart() {
        email.value = ""
        password.value = ""
    }

    fun clearInfo() {
        city.value = ""
        phoneNumber.value = ""
        fullname.value = ""
        imageUrl.value = null
    }

    fun clearSpeciality() {
        mainSpeciality.value = null
        firstAdditionalSpeciality.value = null
        secondAdditionalSpeciality.value = null
    }

    companion object {
        @BindingAdapter("bind:imageUrl")
        @JvmStatic
        fun loadImage(view: CircleImageView, imageUrl: Uri?) {
            imageUrl?.let { it -> setImageGlide(it.toString(), view, android.R.color.white) }
        }
    }




}