package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import kotlinx.android.synthetic.main.fragment_registration_finish.*

class RegistrationFinishFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration_finish, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        btnFinish.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.action_registration_finish_to_login)
        }

    }

}