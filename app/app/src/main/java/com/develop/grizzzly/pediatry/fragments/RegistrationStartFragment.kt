package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.develop.grizzzly.pediatry.R
import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_registration_start.*


class RegistrationStartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration_start, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        tePhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        btnContinue.setOnClickListener {
            navController.navigate(R.id.action_registration_start_to_registration_code)
        }
    }
}