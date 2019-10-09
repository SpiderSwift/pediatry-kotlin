package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.FragmentRegistrationFinishErrorBinding
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_registration_finish_error.*

class RegistrationFinishErrorFragment : Fragment() {

    lateinit var model: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegistrationFinishErrorBinding>(
            inflater,
            R.layout.fragment_registration_finish_error,
            container,
            false
        )

        model = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        }!!
        binding.model = model

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        btnFinish.setOnClickListener {
            navController.navigate(R.id.action_registration_finish_error_to_login)
        }

    }
}