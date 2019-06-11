package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.develop.grizzzly.pediatry.R
import kotlinx.android.synthetic.main.fragment_recovery_password.*

class RecoveryPasswordFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recovery_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        btnRecover.setOnClickListener {
            navController.navigate(R.id.action_recovery_password_to_recovery_finish)
        }

    }
}