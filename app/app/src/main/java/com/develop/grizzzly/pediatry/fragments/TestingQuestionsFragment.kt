package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.model.Question
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_doctors_testing_questions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestingQuestionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_doctors_testing_questions, container, false)

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        GlobalScope.launch {
            val listQuestions = DatabaseAccess.database.questionDao().getQuestionsAll()
                .shuffled() //todo загружать всё сразу не нужно? слишком много данных
            withContext(Dispatchers.Main) {
                var questionNumber = 0
                var isAnswer = false
                val listRadioButton = mutableListOf<RadioButton>(
                    radioButton1, radioButton2, radioButton3, radioButton4
                )
                activity?.toolbarTitle?.visibility = View.GONE
                (activity as? MainActivity)?.supportActionBar?.hide()
                activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                activity?.window?.statusBarColor =
                    activity?.resources?.getColor(android.R.color.white, null) ?: 0
                activity?.bottom_nav?.visibility = View.GONE
                radioGroup.setOnCheckedChangeListener { _: RadioGroup, _: Int ->
                    btnAnswer.isEnabled = true
                }
                updateScreen(listQuestions, questionNumber, listRadioButton)
                nextView.setOnClickListener {
                    if (questionNumber < listQuestions.size - 1) {
                        isAnswer = false
                        questionNumber++
                        transitionToQuestion(listQuestions, questionNumber, listRadioButton)
                    }
                }
                (view.findViewById<View>(R.id.backView)).setOnClickListener {
                    if (questionNumber > 0) {
                        isAnswer = false
                        questionNumber--
                        transitionToQuestion(listQuestions, questionNumber, listRadioButton)
                    }
                }
                btnAnswer.setOnClickListener {
                    if (isAnswer) {
                        if (questionNumber < listQuestions.size - 1) {
                            isAnswer = false
                            questionNumber++
                            transitionToQuestion(listQuestions, questionNumber, listRadioButton)
                        }
                    } else {
                        isAnswer = true
                        btnAnswer.text = getString(R.string.next)
                        setAnswer(
                            listQuestions, listRadioButton, questionNumber,
                            radioGroup.indexOfChild(view.findViewById(radioGroup.checkedRadioButtonId))
                        )
                    }
                }
                super.onViewCreated(view, savedInstanceState)
            }
        }
    }

    private fun updateScreen(
        listQuestions: List<Question>, questionNumber: Int,
        listRadioButton: List<RadioButton>
    ) {
        textQuestion.text = listQuestions[questionNumber].text
        for ((radioButton, btn) in listRadioButton.withIndex())
            btn.text = listQuestions[questionNumber].answers[radioButton].text
        radioGroup.clearCheck()
        btnAnswer.isEnabled = false
        oneToInfinity.text =
            getString(R.string.one_to_infinity, (questionNumber + 1), listQuestions.size)
    }

    private fun setAnswer(
        listQuestions: List<Question>, listRadioButton: MutableList<RadioButton>,
        questionNumber: Int, selectedNumber: Int
    ) {
        if (listQuestions[questionNumber].correctAnswersCombo[0] == selectedNumber) {
            listRadioButton[selectedNumber].setTextColor(
                resources.getColor(android.R.color.holo_green_dark, null)
            )
        } else {
            listRadioButton[selectedNumber].setTextColor(
                resources.getColor(android.R.color.holo_red_dark, null)
            )
            listRadioButton[listQuestions[questionNumber].correctAnswersCombo[0]].setTextColor(
                resources.getColor(android.R.color.holo_green_dark, null)
            )
        }
        for (btn in listRadioButton) btn.isClickable = false
    }

    private fun transitionToQuestion(
        listQuestions: List<Question>, questionNumber: Int, listRadioButton: List<RadioButton>
    ) {
        btnAnswer.text = getString(R.string.to_answer)
        updateScreen(listQuestions, questionNumber, listRadioButton)
        for (btn in listRadioButton) {
            btn.isClickable = true
            btn.setTextColor(resources.getColor(android.R.color.black, null))
        }
    }
}