package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.FragmentRegistrationSpecialityBinding
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Speciality
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_registration_speciality.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationSpecialityFragment : Fragment() {

    lateinit var model: RegistrationViewModel
    lateinit var mainSpecialityList : List<Speciality>
    lateinit var additionalSpecialityList : List<Speciality>
    var currentSpeciality = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentRegistrationSpecialityBinding>(
            inflater,
            R.layout.fragment_registration_speciality,
            container,
            false
        )

        GlobalScope.launch {
            val respAdditional = WebAccess.pediatryApi.getAdditionalSpecialities()
            val respMain = WebAccess.pediatryApi.getMainSpecialities()
            mainSpecialityList = respMain.body()!!.response!!
            additionalSpecialityList = respAdditional.body()!!.response!!
        }


        model = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        }!!



        binding.model = model
        binding.lifecycleOwner = this

        model.mainSpeciality.observe(this, Observer {
            model.specialityValid.value = model.isSpecialityValid()
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        btnMainSpeciality.setOnClickListener {
            currentSpeciality = 1
            picker.data = mainSpecialityList
            specialityLayout.visibility = View.VISIBLE
        }

        btnFirstAdditionalSpeciality.setOnClickListener {
            currentSpeciality = 2
            picker.data = additionalSpecialityList
            specialityLayout.visibility = View.VISIBLE
        }

        btnSecondAdditionalSpeciality.setOnClickListener {
            currentSpeciality = 3
            picker.data = additionalSpecialityList
            specialityLayout.visibility = View.VISIBLE
        }

        tvChoose.setOnClickListener {
            Log.d("TAG", picker.selectedItemPosition.toString())
            when (currentSpeciality) {
                1 -> model.mainSpeciality.value = mainSpecialityList[picker.selectedItemPosition - 1]
                2 -> model.firstAdditionalSpeciality.value = additionalSpecialityList[picker.selectedItemPosition - 1]
                3 -> model.secondAdditionalSpeciality.value = additionalSpecialityList[picker.selectedItemPosition - 1]
            }
            specialityLayout.visibility = View.GONE
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDetach() {
        model.clearSpeciality()
        super.onDetach()
    }
}