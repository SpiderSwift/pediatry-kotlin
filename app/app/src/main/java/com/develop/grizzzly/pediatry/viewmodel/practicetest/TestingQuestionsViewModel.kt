package com.develop.grizzzly.pediatry.viewmodel.practicetest

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel


class TestingQuestionsViewModel : ViewModel() {
    fun answer(view: View) {
        Log.println(Log.ASSERT, "msg", "Answer!")
    }

    fun next(view: View) {
        Log.println(Log.ASSERT, "msg", "Next!")
    }

    fun back(view: View) {
        Log.println(Log.ASSERT, "msg", "Back!")
    }
}