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
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_registration_speciality.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegistrationSpecialityFragment : Fragment() {

    lateinit var model: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentRegistrationSpecialityBinding>(
            inflater,
            R.layout.fragment_registration_speciality,
            container,
            false
        )

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


    override fun onDetach() {
        model.clearSpeciality()
        super.onDetach()
    }
}