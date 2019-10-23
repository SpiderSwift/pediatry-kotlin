package com.develop.grizzzly.pediatry.viewmodel.registration

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.extensions.*
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Speciality
import com.develop.grizzzly.pediatry.util.getPath
import com.develop.grizzzly.pediatry.images.glideRemote
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

private const val TAG = "REGISTRATION VIEW MODEL"

class RegistrationViewModel : ViewModel() {

    val fullname = MutableLiveData<String>().apply { value = "" }
    val email = MutableLiveData<String>().apply { value = "" }
    val password = MutableLiveData<String>().apply { value = "" }
    val city = MutableLiveData<String>().apply { value = "" }
    val fullCity = MutableLiveData<String>().apply { value = "moscow district, " }
    val kladrId = MutableLiveData<String>().apply { value = "5000000123900" }
    val country = MutableLiveData<String>().apply { value = "russia" }
    val mainSpeciality = MutableLiveData<Speciality>().apply { value = null }
    val extraSpec1 = MutableLiveData<Speciality>().apply { value = null }
    val extraSpec2 = MutableLiveData<Speciality>().apply { value = null }
    val phoneNumber = MutableLiveData<String>().apply { value = "" }
    val startValid = MutableLiveData<Boolean>().apply { value = false }
    val infoValid = MutableLiveData<Boolean>().apply { value = false }
    val specialityValid = MutableLiveData<Boolean>().apply { value = false }
    val imageUrl = MutableLiveData<Uri>().apply { value = null }

    val errorMessage: MutableLiveData<String> = MutableLiveData<String>().apply { value = null }

    var fragment: Fragment? = null

    fun onRegistrationStart(view: View) {
        viewModelScope.launch {
            Navigation.findNavController(view)
                .navigateNoExcept(R.id.action_registration_start_to_registration_info)
        }
    }

    fun onRegistrationInfo(view: View) {
        viewModelScope.launch {
            Navigation.findNavController(view)
                .navigateNoExcept(R.id.action_registration_info_to_registration_speciality)
        }
    }

    fun onRegistration(view: View) {
        viewModelScope.launch {
            val fullname = fullname.value?.split(" ")
            val avatar: RequestBody? = try {
                RequestBody.create(
                    MediaType.parse(fragment?.activity?.contentResolver?.getType(imageUrl.value!!)!!),
                    File(getPath(view.context, imageUrl.value!!).toString())
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            val phone = phoneNumber.value?.replace("\\s".toRegex(), "")?.formatPhone().toString()
            val textType = MediaType.parse("text/plain")
            val response = WebAccess.pediatryApi.register(
                RequestBody.create(textType, fullname?.get(1).orEmpty()),
                RequestBody.create(textType, fullname?.get(0).orEmpty()),
                RequestBody.create(textType, fullname?.get(2).orEmpty()),
                RequestBody.create(textType, email.value.orEmpty()),
                RequestBody.create(textType, city.value.orEmpty()),
                RequestBody.create(textType, "${fullCity.value}${city.value}"),
                RequestBody.create(textType, country.value.orEmpty()),
                RequestBody.create(textType, kladrId.value.orEmpty()),
                RequestBody.create(textType, phone),
                RequestBody.create(textType, mainSpeciality.value?.id.toString()),
                RequestBody.create(textType, extraSpec1.value?.toString().orEmpty()),
                RequestBody.create(textType, extraSpec2.value?.toString().orEmpty()),
                avatar,
                RequestBody.create(textType, password.value!!.md5())
            )
            if (response.isSuccessful) {
                Navigation.findNavController(view)
                    .navigateNoExcept(R.id.action_registration_speciality_to_registration_finish)
            } else {
                val result = response.errorBody()?.string().toString()
                try {
                    val jsonObject = JSONObject(result)
                    val errorCode = jsonObject.getInt("code")
                    errorMessage.value = when (errorCode) {
                        410 -> "Неправильные данные о пользователе. Необходимые поля отсутствуют и/или имеют неправильный формат."
                        411 -> "Пользователь с таким email уже зарегистрирован. Попробуйте войти."
                        4121 -> "Файл пользовательского аватара имеет слишком большой размер."
                        4122 -> "Файл пользовательского аватара имеет неверный формат."
                        510 -> "Ошибка регистрации пользователя."
                        else -> "Что-то пошло не так"
                    }
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                    errorMessage.value = "Что-то пошло не так."
                }
                Navigation.findNavController(view)
                    .navigateNoExcept(R.id.action_registration_speciality_to_registration_finish_error)
                //Toast.makeText(view.context, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun onChangePhoto(@Suppress("UNUSED_PARAMETER") view: View) {
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

    private fun checkReadPermission(): Boolean {
        return fragment?.activity?.applicationContext?.let {
            ActivityCompat.checkSelfPermission(
                it, Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } == PackageManager.PERMISSION_GRANTED
    }

    private fun checkWritePermission(): Boolean {
        return fragment?.activity?.applicationContext?.let {
            ActivityCompat.checkSelfPermission(
                it, Manifest.permission.WRITE_EXTERNAL_STORAGE
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
        extraSpec1.value = null
        extraSpec2.value = null
    }

    companion object {
        @BindingAdapter("bind:imageUrl")
        @JvmStatic
        fun loadImage(view: CircleImageView, imageUrl: Uri?) {
            imageUrl?.let { it ->
                glideRemote(
                    it.toString(),
                    view,
                    android.R.color.white
                )
            }
        }
    }

}