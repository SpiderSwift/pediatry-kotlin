package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.FragmentRegistrationStartBinding
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationViewModel

class RegistrationStartFragment : Fragment() {

    lateinit var model : RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentRegistrationStartBinding>(
            inflater,
            R.layout.fragment_registration_start,
            container,
            false
        )



        model = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        }!!

        binding.model = model
        binding.lifecycleOwner = this

        model.email.observe(this, Observer {
            model.startValid.value = model.isStartValid()
        })

        model.password.observe(this, Observer {
            model.startValid.value = model.isStartValid()
        })

        return binding.root
    }

    override fun onDetach() {
        model.clearStart()
        super.onDetach()
    }

}