package com.develop.grizzzly.pediatry.viewmodel.practicetest

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept

class TestingViewModel : ViewModel() {
    fun start(view: View) {
        Log.println(Log.ASSERT, "msg", "Start!")
        Navigation.findNavController(view).navigateNoExcept(R.id.action_testings_to_question)
    }
}