package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.WebAccess
import kotlinx.android.synthetic.main.fragment_registration_speciality.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegistrationSpecialityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration_speciality, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()



        GlobalScope.launch {
            val response = WebAccess.pediatryApi.getMainSpecialities()
            when{
                response.isSuccessful -> {
                    Log.d("TAG", response.toString() + "response body : ${response.body().toString()}")
                }
            }

        }

        btnContinue.setOnClickListener {
            //navController.navigate(R.id.action_registration_speciality_to_registration_finish)
            navController.navigate(R.id.action_registration_speciality_to_specialities_list)
        }
    }
}