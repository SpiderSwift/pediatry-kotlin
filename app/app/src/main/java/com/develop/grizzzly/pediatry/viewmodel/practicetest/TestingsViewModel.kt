package com.develop.grizzzly.pediatry.viewmodel.practicetest

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel

class TestingsViewModel : ViewModel(){
    fun start(view: View) {
        Log.println(Log.ASSERT, "msg", "Start!")
    }
}