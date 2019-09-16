package com.develop.grizzzly.pediatry.fragments

import android.app.Activity
import android.content.Intent
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
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.extensions.addMask
import com.develop.grizzzly.pediatry.extensions.formatPhone
import com.develop.grizzzly.pediatry.util.getPath
import com.develop.grizzzly.pediatry.util.minimizeImage
import com.develop.grizzzly.pediatry.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

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

        model.mainSpeciality.observe(this, Observer {
            btnMainSpeciality.text = model.getMainSpecialityName()
        })

        model.firstAdditionalSpeciality.observe(this, Observer {
            btnFirstAdditionalSpeciality.text = model.getFirstAdditionalSpecialityName()
        })

        model.secondAdditionalSpeciality.observe(this, Observer {
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
            picker.data = model.mainSpecialities
            specialityLayout.visibility = View.VISIBLE
        }

        btnFirstAdditionalSpeciality.setOnClickListener {
            pointer = 0
            currentSpeciality = 2
            picker.data = model.additionalSpecialities
            specialityLayout.visibility = View.VISIBLE
        }

        btnSecondAdditionalSpeciality.setOnClickListener {
            currentSpeciality = 3
            picker.data = model.additionalSpecialities
            specialityLayout.visibility = View.VISIBLE
        }


        picker.setOnItemSelectedListener { _, _, position ->
            pointer = position
        }
        tvChoose.setOnClickListener {
            Log.d("TAG", picker.selectedItemPosition.toString())
            when (currentSpeciality) {
                1 -> model.mainSpeciality.value = model.mainSpecialities[pointer].id
                2 -> model.firstAdditionalSpeciality.value =
                    model.additionalSpecialities[pointer].id
                3 -> model.secondAdditionalSpeciality.value =
                    model.additionalSpecialities[pointer].id
            }
            specialityLayout.visibility = View.GONE
        }


        btnEdit.setOnClickListener {
            GlobalScope.launch {
                var firstAddSpec = ""
                var secondAddSpec = ""
                var requestFile: RequestBody? = null

                if (model.firstAdditionalSpeciality?.value != null) {
                    firstAddSpec = model.firstAdditionalSpeciality.value.toString()
                }
                if (model.secondAdditionalSpeciality?.value != null) {
                    secondAddSpec = model.secondAdditionalSpeciality.value.toString()
                }
                val num = model.phoneNumber.value!!
                    .formatPhone()
                try {
                    val file = File(getPath(view.context, model.newAvatar.value!!))
                    requestFile = RequestBody.create(
                        MediaType.parse(
                            activity?.contentResolver?.getType(model.newAvatar.value!!) ?: ""
                        ), file
                    )
                } catch (ignored: Exception) {

                }
                val textType = MediaType.parse("text/plain")

                val response = WebAccess.pediatryApi.updateProfile(
                    RequestBody.create(textType, model.name.value),
                    RequestBody.create(textType, model.lastname.value),
                    RequestBody.create(textType, model.middlename.value),
                    RequestBody.create(textType, model.email.value),
                    RequestBody.create(textType, model.city.value),
                    RequestBody.create(textType, num),
                    RequestBody.create(textType, model.mainSpeciality.value.toString()),
                    RequestBody.create(textType, firstAddSpec),
                    RequestBody.create(textType, secondAddSpec),
                    requestFile
                )
                if (response.isSuccessful) {
                    Log.d(TAG, response.toString())
                    Log.d(TAG, response.body()?.string())
                } else {
                    Log.d(TAG, response.toString())
                    Log.d(TAG, response.errorBody()?.string())
                }

                withContext(Dispatchers.Main) {
                    activity?.onBackPressed()
                }


            }

        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data ?: return
        if (resultCode == Activity.RESULT_OK) {
            val finalImage = minimizeImage(
                uri = data.data,
                contentResolver = context!!.contentResolver
            )

            model.newAvatar.postValue(finalImage)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}