package com.develop.grizzzly.pediatry.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.model.Question
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestingQuestionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_doctors_testing_questions, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var list: List<Question>? = null
        GlobalScope.launch(Dispatchers.Main) {
            list = withContext(Dispatchers.Default) {
                DatabaseAccess.database.questionDao().getQuestions()
            }
        }
        Log.println(Log.ASSERT, "msg: ", list!!.size.toString())
        var questionNumber = 1
        val imageView = view.findViewById<ImageView>(R.id.testing_image)
        val mainActivity = activity as? MainActivity
        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val btnNext = view.findViewById<Button>(R.id.btnAnswer)
        val questionNumberTextView = view.findViewById<TextView>(R.id.one_to_ten)
        val textQuestion = view.findViewById<TextView>(R.id.text_question)
        val listRadioButton = listOf<RadioButton>(
            view.findViewById(R.id.radioButton1),
            view.findViewById(R.id.radioButton2),
            view.findViewById(R.id.radioButton3),
            view.findViewById(R.id.radioButton4)
        )
        activity?.toolbarTitle?.visibility = View.GONE
        mainActivity?.supportActionBar?.hide()
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //window?.statusBarColor = activity?.resources?.getColor(android.R.color.white, null) ?: 0
        activity?.bottom_nav?.visibility = View.GONE
        radioGroup.setOnCheckedChangeListener { _: RadioGroup, _: Int ->
            btnNext.isEnabled = true
        }
        editView(
            list!!,
            questionNumber,
            imageView,
            textQuestion,
            questionNumberTextView,
            listRadioButton,
            btnNext,
            radioGroup
        )
        (view.findViewById<View>(R.id.nextView)).setOnClickListener {
            if (questionNumber < 50) {
                questionNumber++
                editView(
                    list!!,
                    questionNumber,
                    imageView,
                    textQuestion,
                    questionNumberTextView,
                    listRadioButton,
                    btnNext,
                    radioGroup
                )
            }
        }
        (view.findViewById<View>(R.id.backView)).setOnClickListener {
            if (questionNumber > 1) {
                questionNumber--
                editView(
                    list!!,
                    questionNumber,
                    imageView,
                    textQuestion,
                    questionNumberTextView,
                    listRadioButton,
                    btnNext,
                    radioGroup
                )
            }
        }
        btnNext.setOnClickListener {
            when (radioGroup.indexOfChild(view.findViewById(radioGroup.checkedRadioButtonId))) {
                0 -> Log.println(Log.ASSERT, "msg: ", "1")
                1 -> Log.println(Log.ASSERT, "msg: ", "2")
                2 -> Log.println(Log.ASSERT, "msg: ", "3")
                3 -> Log.println(Log.ASSERT, "msg: ", "4")
                else -> Log.println(Log.ASSERT, "msg: ", "Error!!!")
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    fun editView(
        list: List<Question>,
        questionNumber: Int,
        imageView: ImageView,
        textQuestion: TextView,
        questionNumberTextView: TextView,
        listRadioButton: List<RadioButton>,
        btnNext: Button,
        radioGroup: RadioGroup
    ) {
        Picasso.get()
            .load(list[questionNumber].imageUrl)
            .into(imageView)
        textQuestion.text = list[questionNumber].textQuestion
        for ((x, btn) in listRadioButton.withIndex()) {
            btn.text = list[questionNumber].listAnswers[x].text
        }
        radioGroup.clearCheck()
        btnNext.isEnabled = false
        questionNumberTextView.text =
            "$questionNumber " + getString(R.string.one_to_infinity)
    }
}