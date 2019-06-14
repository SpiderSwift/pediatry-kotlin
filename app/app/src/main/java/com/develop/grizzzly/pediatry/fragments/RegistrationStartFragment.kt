package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.develop.grizzzly.pediatry.R
import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.develop.grizzzly.pediatry.databinding.FragmentRegistrationStartBinding
import com.develop.grizzzly.pediatry.util.isPhoneNumber
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationStartViewModel
import kotlinx.android.synthetic.main.fragment_registration_start.*

class RegistrationStartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentRegistrationStartBinding>(
            inflater,
            R.layout.fragment_registration_start,
            container,
            false
        )

        val model = ViewModelProviders.of(this).get(RegistrationStartViewModel::class.java)

        binding.model = model
        binding.lifecycleOwner = this

        model.phoneNumber.observe(this, Observer {
            model.valid.value = it.isPhoneNumber()
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tePhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }
}