package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*


class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val myActivity = activity as MainActivity?
        myActivity?.supportActionBar?.show()
        myActivity?.bottom_nav?.visibility = View.VISIBLE

        return inflater.inflate(R.layout.fragment_news, container, false)
    }
}
