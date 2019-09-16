package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.FragmentProfileEditBinding
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.util.addMask
import com.develop.grizzzly.pediatry.viewmodel.profile.ProfileViewModel
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "PROFILE EDIT FRAGMENT"

class ProfileEditFragment: Fragment() {

    lateinit var model : ProfileViewModel
    var currentSpeciality = 0
    var pointer = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                2 -> model.firstAdditionalSpeciality.value = model.additionalSpecialities[pointer].id
                3 -> model.secondAdditionalSpeciality.value = model.additionalSpecialities[pointer].id
            }
            specialityLayout.visibility = View.GONE
        }


        btnEdit.setOnClickListener {
            GlobalScope.launch {
                val response = WebAccess.pediatryApi.updateProfile(
                    model.name.value,
                    model.lastname.value,
                    model.middlename.value,
                    model.email.value,
                    model.city.value,
                    model.phoneNumber.value,
                    model.mainSpeciality.value.toString(),
                    model.firstAdditionalSpeciality.value.toString(),
                    model.secondAdditionalSpeciality.value.toString()
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



}