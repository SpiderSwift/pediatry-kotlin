package com.develop.grizzzly.pediatry.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.FragmentProfileEditBinding
import com.develop.grizzzly.pediatry.extensions.addMask
import com.develop.grizzzly.pediatry.extensions.formatPhone
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.util.getPath
import com.develop.grizzzly.pediatry.util.minimizeImage
import com.develop.grizzzly.pediatry.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

private const val TAG = "PROFILE EDIT FRAGMENT"

class ProfileEditFragment : Fragment() {

    lateinit var model: ProfileViewModel
    private var currentSpeciality = 0
    private var pointer = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileEditBinding>(
            inflater,
            R.layout.fragment_profile_edit,
            container,
            false
        )
        val mainActivity = activity
        mainActivity?.toolbarTitle?.text = "Редактирование"
        model = activity?.run {
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        }!!
        model.fragment = this
        binding.model = model
        binding.lifecycleOwner = this
        binding.phoneEditText.addMask("+7 ([000]) [000]-[00]-[00]")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.bottom_nav?.visibility = View.GONE
        if (verifyAvailableNetwork()) {
            GlobalScope.launch(Dispatchers.Main) {
                delay(200)
                mainContent.visibility = View.VISIBLE
                load.visibility = View.GONE
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                delay(200)
                errorMsg.visibility = View.VISIBLE
                load.visibility = View.GONE
            }
        }
        model.mainSpec.observe(this, Observer {
            btnMainSpeciality.text = model.getMainSpecialityName()
        })
        model.extraSpec1.observe(this, Observer {
            btnFirstAdditionalSpeciality.text = model.getFirstAdditionalSpecialityName()
        })
        model.extraSpec2.observe(this, Observer {
            btnSecondAdditionalSpeciality.text = model.getSecondAdditionalSpecialityName()
        })
        model.newAvatar.observe(this, Observer {
            if (it != null) {
                profile_edit_photo.setImageURI(it)
            }
        })
        btnMainSpeciality.setOnClickListener {
            pointer = 0
            picker.setSelectedItemPosition(pointer, false)
            currentSpeciality = 1
            picker.data = model.mainSpecs
            specialityLayout.visibility = View.VISIBLE
        }
        btnFirstAdditionalSpeciality.setOnClickListener {
            pointer = 0
            currentSpeciality = 2
            picker.data = model.extraSpecs
            specialityLayout.visibility = View.VISIBLE
        }
        btnSecondAdditionalSpeciality.setOnClickListener {
            currentSpeciality = 3
            picker.data = model.extraSpecs
            specialityLayout.visibility = View.VISIBLE
        }
        picker.setOnItemSelectedListener { _, _, position ->
            pointer = position
        }
        tvChoose.setOnClickListener {
            Log.d("TAG", picker.selectedItemPosition.toString())
            when (currentSpeciality) {
                1 -> model.mainSpec.value = model.mainSpecs[pointer].id
                2 -> model.extraSpec1.value = model.extraSpecs[pointer].id
                3 -> model.extraSpec2.value = model.extraSpecs[pointer].id
            }
            specialityLayout.visibility = View.GONE
        }
        btnEdit.setOnClickListener {
            GlobalScope.launch {
                try {
                    val phone = model.phoneNumber.value!!.formatPhone()
                    val avatar: RequestBody? = try {
                        RequestBody.create(
                            MediaType.parse(activity?.contentResolver?.getType(model.newAvatar.value!!)!!),
                            File(getPath(view.context, model.newAvatar.value!!).toString())
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                    val textType = MediaType.parse("text/plain")
                    WebAccess.pediatryApi.updateProfile(
                        RequestBody.create(textType, model.name.value.orEmpty()),
                        RequestBody.create(textType, model.lastname.value.orEmpty()),
                        RequestBody.create(textType, model.middlename.value.orEmpty()),
                        RequestBody.create(textType, model.email.value.orEmpty()),
                        RequestBody.create(textType, model.city.value.orEmpty()),
                        RequestBody.create(textType, model.fullCity.value.orEmpty()),
                        RequestBody.create(textType, model.country.value.orEmpty()),
                        RequestBody.create(textType, model.kladrId.value.orEmpty()),
                        RequestBody.create(textType, phone),
                        RequestBody.create(textType, model.mainSpec.value.toString()),
                        RequestBody.create(textType, model.extraSpec1.value?.toString().orEmpty()),
                        RequestBody.create(textType, model.extraSpec2.value?.toString().orEmpty()),
                        avatar
                    )
                    withContext(Dispatchers.Main) {
                        activity?.onBackPressed()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        activity?.onBackPressed()
                    }
                }

            }

        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun verifyAvailableNetwork(): Boolean {
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data ?: return
        if (resultCode == Activity.RESULT_OK) {
            val finalImage = data.data?.let {
                minimizeImage(
                    uri = it,
                    contentResolver = context!!.contentResolver
                )
            }

            model.newAvatar.postValue(finalImage)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}