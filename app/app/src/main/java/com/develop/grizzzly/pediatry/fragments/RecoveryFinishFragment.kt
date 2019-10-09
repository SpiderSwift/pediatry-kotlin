package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.FragmentRecoveryFinishBinding
import com.develop.grizzzly.pediatry.viewmodel.recovery.RecoveryViewModel


class RecoveryFinishFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRecoveryFinishBinding>(
            inflater,
            R.layout.fragment_recovery_finish,
            container,
            false
        )

        val model = ViewModelProviders.of(this).get(RecoveryViewModel::class.java)

        binding.model = model
        binding.lifecycleOwner = this

        return binding.root
    }


}