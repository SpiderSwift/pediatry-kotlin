package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_messages.*

import com.develop.grizzzly.pediatry.R

class MessagesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_text.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_messages_btn_to_hello_btn)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
