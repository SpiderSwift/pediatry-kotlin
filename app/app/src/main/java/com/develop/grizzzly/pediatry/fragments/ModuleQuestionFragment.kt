package com.develop.grizzzly.pediatry.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.AnswerInstance
import com.develop.grizzzly.pediatry.network.model.Question
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_error.*
import kotlinx.android.synthetic.main.fragment_module_question.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModuleQuestionFragment : Fragment() {

    private val args: ModuleQuestionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val mainActivity = activity as? MainActivity
        mainActivity?.supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_module_question, container, false)
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        GlobalScope.launch {
            val listQuestions: List<Question>
            val listCorrectAnswers = mutableListOf<AnswerInstance>()
            try {
                listQuestions = DatabaseAccess.database.questionDao()
                    .getListQuestionsByIds(WebAccess.pediatryApi.getModulesQuestion(args.moduleId.toString()).body()!!.response!!)
                if (listQuestions.isEmpty())
                    throw IllegalArgumentException("Этот тест сейчас пустой :(")
            } catch (e: IllegalArgumentException) {
                withContext(Dispatchers.Main) {
                    errorView()
                    errorMsg.text = e.message.toString()
                }
                return@launch
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    errorView()
                    errorMsg.text = "Ошибка :("
                }
                return@launch
            }
            withContext(Dispatchers.Main) {
                listQuestions.forEach {
                    listCorrectAnswers.add(
                        AnswerInstance(it.correctAnswersCombo[0], AnswerInstance.noAnswered)
                    )
                }
                progressBarView.visibility = View.GONE
                moduleQuestionConstraintLayout.visibility = View.VISIBLE
                var questionNumber = 0
                var isAnswer = false
                val listRadioButton = mutableListOf<RadioButton>(
                    radioButton1, radioButton2, radioButton3, radioButton4
                )
                activity?.toolbarTitle?.visibility = View.GONE
                val window = activity?.window
                window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window?.statusBarColor =
                    activity?.resources?.getColor(android.R.color.white, null) ?: 0
                activity?.bottom_nav?.visibility = View.GONE
                radioGroup.setOnCheckedChangeListener { _, _ -> btnAnswer.isEnabled = true }
                btnResult.setOnClickListener { activity?.onBackPressed() }
                updateScreen(listQuestions, questionNumber, listRadioButton)
                nextView.setOnClickListener {
                    if (questionNumber < listQuestions.size - 1) {
                        isAnswer = false
                        questionNumber++
                        transitionToQuestion(listQuestions, questionNumber, listRadioButton)
                    }
                }
                backView.setOnClickListener {
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
                            radioGroup.indexOfChild(view.findViewById(radioGroup.checkedRadioButtonId)),
                            listCorrectAnswers
                        )
                        isAllAnswered(listCorrectAnswers, listQuestions.size, listRadioButton)
                    }
                }
                super.onViewCreated(view, savedInstanceState)
            }
        }
    }

    private fun updateScreen(
        listQuestions: List<Question>, questionNumber: Int, listRadioButton: List<RadioButton>
    ) {
        textQuestion.text = listQuestions[questionNumber].text
        for ((x, btn) in listRadioButton.withIndex())
            btn.text = listQuestions[questionNumber].answers[x].text
        radioGroup.clearCheck()
        btnAnswer.isEnabled = false
        oneToTen.text = getString(R.string.one_to_ten, (questionNumber + 1))
    }

    private fun setAnswer(
        listQuestions: List<Question>, listRadioButton: MutableList<RadioButton>,
        questionNumber: Int, selectedNumber: Int, listCorrectAnswers: MutableList<AnswerInstance>
    ) {
        if (listQuestions[questionNumber].correctAnswersCombo[0] == selectedNumber) {
            listRadioButton[selectedNumber].setTextColor(
                resources.getColor(android.R.color.holo_green_dark, null)
            )
        }
        else {
            listRadioButton[selectedNumber].setTextColor(
                resources.getColor(android.R.color.holo_red_dark, null)
            )
            listRadioButton[listQuestions[questionNumber].correctAnswersCombo[0]].setTextColor(
                resources.getColor(android.R.color.holo_green_dark, null)
            )
        }
        listCorrectAnswers[questionNumber].selectedAnswer = selectedNumber
        for (btn in listRadioButton) btn.isClickable = false
    }

    private fun errorView() {
        progressBarView.visibility = View.GONE
        includeError.visibility = View.VISIBLE
    }

    private fun isAllAnswered(
        listCorrectAnswers: List<AnswerInstance>,
        listQuestionsSize: Int, listRadioButton: List<RadioButton>
    ) {
        if (!listCorrectAnswers.map { it.selectedAnswer }.contains(AnswerInstance.noAnswered)) {
            btnAnswer.isClickable = false
            backView.isClickable = false
            nextView.isClickable = false
            listRadioButton.forEach { it.isClickable = false }
            moduleQuestionResult.visibility = View.VISIBLE
            result.text = getString(
                R.string.result_exam,
                listCorrectAnswers.filter { it.correctAnswer == it.selectedAnswer }.size,
                listCorrectAnswers.size,
                if (listCorrectAnswers.filter { it.correctAnswer == it.selectedAnswer }.size >= 8)
                    getString(R.string.exam_passed) else getString(R.string.exam_not_passed)
            )
            GlobalScope.launch {
                WebAccess.pediatryApi.setModuleResult(
                    args.moduleId.toString(), listQuestionsSize,
                    listCorrectAnswers.filter { it.correctAnswer == it.selectedAnswer }.size
                )
            }
        }
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