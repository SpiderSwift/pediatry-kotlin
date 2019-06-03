package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_messages.*

import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity


class MessagesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_messages, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_text.setOnClickListener {
            val navController = Navigation.findNavController(activity!!, R.id.bottomNavFragment)
            navController.navigate(R.id.action_messages_btn_to_hello_btn)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
