package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.FragmentRegistrationSpecialityBinding
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Speciality
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_registration_speciality.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegistrationSpecialityFragment : Fragment() {

    lateinit var model: RegistrationViewModel
    lateinit var mainSpecs: List<Speciality>
    lateinit var extraSpecs: List<Speciality>
    var currentSpeciality = 0
    var pointer = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegistrationSpecialityBinding>(
            inflater,
            R.layout.fragment_registration_speciality,
            container,
            false
        )
        GlobalScope.launch {
            try {
                val extraSpecsResult = WebAccess.pediatryApi.getExtraSpecs()
                val mainSpecsResult = WebAccess.pediatryApi.getMainSpecs()
                mainSpecs = mainSpecsResult.body()!!.response!!
                extraSpecs = extraSpecsResult.body()!!.response!!
            } catch (e: Exception) {
                mainSpecs = listOf()
                extraSpecs = listOf()
            }
            btnMainSpec.setOnClickListener {
                pointer = 0
                picker.setSelectedItemPosition(pointer, false)
                currentSpeciality = 1
                picker.data = mainSpecs
                specialityLayout.visibility = View.VISIBLE
            }
            picker.setOnItemSelectedListener { _, _, position ->
                pointer = position
            }
            tvChoose.setOnClickListener {
                try {
                    Log.d("TAG", picker.selectedItemPosition.toString())
                    when (currentSpeciality) {
                        1 -> model.mainSpeciality.value = mainSpecs[pointer]
                        2 -> model.extraSpec1.value = extraSpecs[pointer]
                        3 -> model.extraSpec2.value = extraSpecs[pointer]
                    }
                    specialityLayout.visibility = View.GONE
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        model = activity?.run {
            ViewModelProvider(this).get(RegistrationViewModel::class.java)
        }!!
        binding.model = model
        binding.lifecycleOwner = this
        model.mainSpeciality.observe(this, Observer {
            model.specialityValid.value = model.isSpecialityValid()
        })
        return binding.root
    }

    override fun onDetach() {
        model.clearSpeciality()
        super.onDetach()
    }

}