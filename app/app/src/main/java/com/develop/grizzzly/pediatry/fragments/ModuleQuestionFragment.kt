package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Question
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ModuleQuestionFragment : Fragment() {

    private val args: ModuleQuestionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as? MainActivity
        mainActivity?.supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_module_question, container, false)
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        GlobalScope.launch {
            val listQuestions: List<Question>
            val listCorrectAnswers = mutableListOf<Int>()
            try {
                listQuestions = DatabaseAccess.database.questionDao()
                    .getListQuestionsByIds(WebAccess.pediatryApi.getModulesQuestion(args.moduleId.toString()).body()!!.response!!)
            } catch (e: Exception) {
                return@launch
            }
            // 0 - no answer   // 1 - true   // -1 - false
            listQuestions.forEach { _ -> listCorrectAnswers.add(0) }
            withContext(Dispatchers.Main) {
                var questionNumber = 0
                val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
                val btnAnswer = view.findViewById<Button>(R.id.btnAnswer)
                val questionNumberTextView = view.findViewById<TextView>(R.id.one_to_infinity)
                val textQuestion = view.findViewById<TextView>(R.id.text_question)
                var isAnswer = false
                val backButton = view.findViewById<View>(R.id.backView)
                val nextButton = view.findViewById<View>(R.id.nextView)
                val listRadioButton = mutableListOf<RadioButton>(
                    view.findViewById(R.id.radioButton1), view.findViewById(R.id.radioButton2),
                    view.findViewById(R.id.radioButton3), view.findViewById(R.id.radioButton4)
                )
                activity?.toolbarTitle?.visibility = View.GONE
                val window = activity?.window
                window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window?.statusBarColor =
                    activity?.resources?.getColor(android.R.color.white, null) ?: 0
                activity?.bottom_nav?.visibility = View.GONE
                radioGroup.setOnCheckedChangeListener { _: RadioGroup, _: Int ->
                    btnAnswer.isEnabled = true
                }
                (view.findViewById<Button>(R.id.btnResult)).setOnClickListener { activity?.onBackPressed() }
                updateScreen(
                    listQuestions, questionNumber, textQuestion,
                    questionNumberTextView, listRadioButton,
                    btnAnswer, radioGroup
                )
                nextButton.setOnClickListener {
                    if (questionNumber < listQuestions.size - 1) {
                        isAnswer = false
                        btnAnswer.text = getString(R.string.to_answer)
                        questionNumber++
                        updateScreen(
                            listQuestions, questionNumber,
                            textQuestion,
                            questionNumberTextView, listRadioButton,
                            btnAnswer, radioGroup
                        )
                        for (btn in listRadioButton) {
                            btn.isClickable = true
                            btn.setTextColor(resources.getColor(android.R.color.black, null))
                        }
                    }
                }
                backButton.setOnClickListener {
                    if (questionNumber > 0) {
                        isAnswer = false
                        btnAnswer.text = getString(R.string.to_answer)
                        questionNumber--
                        updateScreen(
                            listQuestions, questionNumber, textQuestion, questionNumberTextView,
                            listRadioButton, btnAnswer, radioGroup
                        )
                        for (btn in listRadioButton) {
                            btn.isClickable = true
                            btn.setTextColor(resources.getColor(android.R.color.black, null))
                        }
                    }
                }
                btnAnswer.setOnClickListener {
                    if (isAnswer) {
                        if (questionNumber < listQuestions.size - 1) {
                            isAnswer = false
                            btnAnswer.text = getString(R.string.to_answer)
                            questionNumber++
                            updateScreen(
                                listQuestions, questionNumber, textQuestion,
                                questionNumberTextView, listRadioButton, btnAnswer, radioGroup
                            )
                            for (btn in listRadioButton) {
                                btn.isClickable = true
                                btn.setTextColor(resources.getColor(android.R.color.black, null))
                            }
                        }
                    } else {
                        isAnswer = true
                        btnAnswer.text = getString(R.string.next)
                        setAnswer(
                            listQuestions, listRadioButton, questionNumber,
                            radioGroup.indexOfChild(view.findViewById(radioGroup.checkedRadioButtonId)),
                            listCorrectAnswers
                        )
                        if (!listCorrectAnswers.contains(0)) {
                            btnAnswer.isClickable = false
                            backButton.isClickable = false
                            nextButton.isClickable = false
                            listRadioButton.forEach { it.isClickable = false }
                            view.findViewById<ConstraintLayout>(R.id.moduleQuestionResult)
                                .visibility = View.VISIBLE
                            view.findViewById<TextView>(R.id.result).text =
                                getString(
                                    R.string.result_exam,
                                    Collections.frequency(listCorrectAnswers, 1),
                                    listCorrectAnswers.size,
                                    if (Collections.frequency(listCorrectAnswers, 1) >= 8)
                                        getString(R.string.exam_passed) else getString(R.string.exam_not_passed)
                                )
                            //todo отправить результат на сервер
                        }
                    }
                }
                super.onViewCreated(view, savedInstanceState)
            }
        }
    }

    private fun updateScreen(
        list: List<Question>, questionNumber: Int,
        textQuestion: TextView,
        questionNumberTextView: TextView, listRadioButton: List<RadioButton>,
        btnNext: Button, radioGroup: RadioGroup
    ) {
        textQuestion.text = list[questionNumber].text
        for ((x, btn) in listRadioButton.withIndex()) btn.text =
            list[questionNumber].answers[x].text
        radioGroup.clearCheck()
        btnNext.isEnabled = false
        questionNumberTextView.text = getString(R.string.one_to_ten, (questionNumber + 1))
    }

    private fun setAnswer(
        listQuestions: List<Question>, listRadioButton: MutableList<RadioButton>,
        questionNumber: Int, selectedNumber: Int, listCorrectAnswers: MutableList<Int>
    ) {
        if (listQuestions[questionNumber].correctAnswersCombo[0] == selectedNumber) {
            listCorrectAnswers[questionNumber] = 1
            listRadioButton[selectedNumber].setTextColor(
                resources.getColor(android.R.color.holo_green_dark, null)
            )
        } else {
            listCorrectAnswers[questionNumber] = -1
            listRadioButton[selectedNumber].setTextColor(
                resources.getColor(android.R.color.holo_red_dark, null)
            )
            listRadioButton[listQuestions[questionNumber].correctAnswersCombo[0]].setTextColor(
                resources.getColor(android.R.color.holo_green_dark, null)
            )
        }
        for (btn in listRadioButton) {
            btn.isClickable = false
        }
    }
}