package com.develop.grizzzly.pediatry.viewmodel.registration

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.extensions.formatPhone
import com.develop.grizzzly.pediatry.extensions.isEmail
import com.develop.grizzzly.pediatry.extensions.isPhoneNumber
import com.develop.grizzzly.pediatry.extensions.md5
import kotlinx.coroutines.launch

import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Speciality
import com.develop.grizzzly.pediatry.util.*

import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.RequestBody
import okhttp3.MediaType
import org.json.JSONObject
import java.io.File
import java.lang.Exception


private const val TAG = "REGISTRATION VIEW MODEL"

class RegistrationViewModel : ViewModel() {

    val email = MutableLiveData<String>().apply { value = "" }
    val password = MutableLiveData<String>().apply { value = "" }
    val country = MutableLiveData<String>().apply { value="Россия" }
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

    val errorMessage: MutableLiveData<String> = MutableLiveData<String>().apply { value = null }

    var fragment: Fragment? = null


    fun onRegistrationStart(view: View) {
        viewModelScope.launch {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_registration_start_to_registration_info)
        }
    }

    fun onRegistrationInfo(view: View) {
        viewModelScope.launch {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_registration_info_to_registration_speciality)
        }
    }

    fun onRegistration(view: View) {
        viewModelScope.launch {

            val fullname = fullname.value?.split(" ")


            var requestFile: RequestBody? = null

            try {
                val file = File(getPath(view.context, imageUrl.value!!))
                requestFile = RequestBody.create(
                    MediaType.parse(
                        fragment?.activity?.contentResolver?.getType(imageUrl.value!!) ?: ""
                    ), file
                )
            } catch (ignored: Exception) {

            }


            var stringAdd1 = ""
            if (firstAdditionalSpeciality.value?.id != null) {
                stringAdd1 = firstAdditionalSpeciality.value?.id.toString()
            }

            var stringAdd2 = ""
            if (secondAdditionalSpeciality.value?.id != null) {
                stringAdd2 = secondAdditionalSpeciality.value?.id.toString()
            }

            val num = phoneNumber.value!!
                .replace("\\s".toRegex(), "")
                .formatPhone()


            val textType = MediaType.parse("text/plain")

            val response = WebAccess.pediatryApi.register(
                RequestBody.create(textType, fullname!![1]),
                RequestBody.create(textType, fullname[0]),
                RequestBody.create(textType, fullname[2]),
                RequestBody.create(textType, email.value!!),
                RequestBody.create(textType, country.value!!),
                RequestBody.create(textType, city.value!!),
                RequestBody.create(textType, num),
                RequestBody.create(textType, mainSpeciality.value!!.id.toString()),
                RequestBody.create(textType, stringAdd1),
                RequestBody.create(textType, stringAdd2),
                RequestBody.create(textType, password.value!!.md5()),
                requestFile,
                RequestBody.create(textType, "Московская обл, ${country.value!!}"),
                RequestBody.create(textType, "5000000123900")
            )

            if (response.isSuccessful) {
                Log.d(TAG, response.toString())
                Log.d(TAG, response.body()?.string())
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_registration_speciality_to_registration_finish)
            } else {
                val result = response.errorBody()?.string().toString()
                Log.d(TAG, response.toString())
                Log.d(TAG, result)

                try {
                    val jsonObject = JSONObject(result)
                    val errorCode = jsonObject.getInt("code")
                    errorMessage.value = when(errorCode){
                        410 -> "Неправильные данные о пользователе. Необходимые поля отсутствуют и/или имеют неправильный формат."
                        411 -> "Пользователь с таким email уже зарегистрирован. Попробуйте войти."
                        4121 -> "Файл пользовательского аватара имеет слишком большой размер."
                        4122 -> "Файл пользовательского аватара имеет неверный формат."
                        510 -> "Ошибка регистрации пользователя."
                        else -> "Что-то пошло не так"
                    }
                } catch (e: Exception){
                    Log.e(TAG, e.toString())
                    errorMessage.value = "Что-то пошло не так."
                }
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_registration_speciality_to_registration_finish_error)
                //Toast.makeText(view.context, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
            }


        }
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
                Array(2) { Manifest.permission.READ_EXTERNAL_STORAGE
                Manifest.permission.WRITE_EXTERNAL_STORAGE},
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


    fun isStartValid(): Boolean {
        return (email.value?.isEmail() ?: false) and (password.value?.isNotEmpty() ?: false)
    }

    fun isInfoValid(): Boolean {
        return (isValidFullname()) and (phoneNumber.value?.isPhoneNumber()
            ?: false) and (city.value?.isNotEmpty() ?: false)
    }

    fun isValidCity(): Boolean {
        return city.value?.isNotEmpty() ?: false
    }

    fun isValidFullname(): Boolean {
        val list = fullname.value?.split(" ")
        return (list?.size == 3) and (list?.getOrNull(2)?.length != 0)
    }

    fun isValidPhone(): Boolean {
        return phoneNumber.value?.isPhoneNumber() ?: false
    }

    fun isSpecialityValid(): Boolean {
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