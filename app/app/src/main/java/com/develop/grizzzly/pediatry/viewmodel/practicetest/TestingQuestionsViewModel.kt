package com.develop.grizzzly.pediatry.viewmodel.practicetest

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.fragments.TestingsQuestionsFragment

class TestingQuestionsViewModel : ViewModel() {
    fun answer(view: View) {
        view as TextView
        view.setText(TestingsQuestionsFragment.size)

        Log.println(Log.ASSERT, "msg", "Answer!")
    }

    fun next(view: View) {
        Log.println(Log.ASSERT, "msg", "Next!")
    }

    fun back(view: View) {
        Log.println(Log.ASSERT, "msg", "Back!")
    }
}