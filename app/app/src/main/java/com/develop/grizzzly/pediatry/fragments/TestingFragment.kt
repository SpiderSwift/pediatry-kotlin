package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import kotlinx.android.synthetic.main.activity_main.*

class TestingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_doctors_testing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainActivity = activity as? MainActivity
        activity?.toolbarTitle?.visibility = View.GONE
        mainActivity?.supportActionBar?.hide()
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //window?.statusBarColor = activity?.resources?.getColor(android.R.color.white, null) ?: 0 Todo api<21
        activity?.bottom_nav?.visibility = View.GONE
        (view.findViewById<Button>(R.id.btnStartTest)).setOnClickListener {
            Navigation.findNavController(view).navigateNoExcept(R.id.action_testings_to_question)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}