package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.util.Log
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.WebAccess
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WebinarsFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        GlobalScope.launch {
            val response = WebAccess.pediatryApi.getConferences(0, 10)
            if (response.isSuccessful) {
                Log.d("TAG", response.body()?.response.toString())
            }
        }
        return inflater.inflate(R.layout.fragment_webinars, container, false)
    }

}