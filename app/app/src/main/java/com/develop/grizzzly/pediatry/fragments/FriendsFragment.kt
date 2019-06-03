package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.develop.grizzzly.pediatry.R

class FriendsFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_friends, container, false)
}