package com.develop.grizzzly.pediatry.viewmodel.practicetest

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel

class TestingsViewMode : ViewModel() {

    fun test(viev: View) {


        Log.println(Log.ASSERT,"msg: ","start!")
    }

}