package com.develop.grizzzly.pediatry.viewmodel.practicetest

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.FragmentDoctorsTestingQuestionsBinding

class TestingQuestionsViewModel : ViewModel() {

   lateinit var binding: FragmentDoctorsTestingQuestionsBinding

    var questionNumber: Int = 0

    fun answer(view: View) {
        Log.println(Log.ASSERT, "msg", "Answer!")
    }

    fun next(view: View) {
        view as TextView
        view.setText(++questionNumber)
        Log.println(Log.ASSERT, "msg", questionNumber.toString())
    }

    fun back(view: View) {
        view as TextView
        view.setText(--questionNumber)
        Log.println(Log.ASSERT, "msg", questionNumber.toString().toString())
    }
}