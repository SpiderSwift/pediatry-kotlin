package com.develop.grizzzly.pediatry.fragments


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.develop.grizzzly.pediatry.databinding.FragmentLoginBinding
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.viewmodel.login.LoginViewModel

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        val model = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding.model = model
        binding.lifecycleOwner = this

        return binding.root
    }








}


