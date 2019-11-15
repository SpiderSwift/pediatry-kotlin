package com.develop.grizzzly.pediatry.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.adapters.module.ResultModuleAdapter
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.Question
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModuleQuestionFragment : Fragment() { //todo сократить

    private val args: ModuleQuestionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_module_question, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) { //Todo List<Boolean> | isAnswered
        GlobalScope.launch {
            val listQuestions = mutableListOf<Question>()
            WebAccess.pediatryApi.getModulesQuestion(args.moduleId.toString()).body()!!.response!!.forEach {
                listQuestions.add(DatabaseAccess.database.questionDao().getQuestionsFromModule(it))
            }
            Log.println(Log.ASSERT, "msg", listQuestions.size.toString())
            // 0 - no answer
            // 1 - true
            // -1 - false
            val listResult = view.findViewById<RecyclerView>(R.id.listResults)
            val listCorrectAnswers = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            withContext(Dispatchers.Main) {
                var questionNumber = 0
                val imageView = view.findViewById<ImageView>(R.id.testing_image)
                val mainActivity = activity as? MainActivity
                val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
                val btnNext = view.findViewById<Button>(R.id.btnAnswer)
                val questionNumberTextView = view.findViewById<TextView>(R.id.one_to_ten)
                val textQuestion = view.findViewById<TextView>(R.id.text_question)
                var isAnswer = false
                val listRadioButton = mutableListOf<RadioButton>(
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
                window?.statusBarColor =
                    activity?.resources?.getColor(android.R.color.white, null) ?: 0
                activity?.bottom_nav?.visibility = View.GONE
                radioGroup.setOnCheckedChangeListener { _: RadioGroup, _: Int ->
                    btnNext.isEnabled = true
                }
                editView(
                    listQuestions,
                    questionNumber,
                    imageView,
                    textQuestion,
                    questionNumberTextView,
                    listRadioButton,
                    btnNext,
                    radioGroup
                )
                (view.findViewById<View>(R.id.nextView)).setOnClickListener {
                    if (questionNumber < listQuestions.size - 1) {
                        isAnswer = false
                        btnNext.text = getString(R.string.to_answer)
                        questionNumber++
                        editView(
                            listQuestions,
                            questionNumber,
                            imageView,
                            textQuestion,
                            questionNumberTextView,
                            listRadioButton,
                            btnNext,
                            radioGroup
                        )
                        for (btn in listRadioButton) {
                            btn.isClickable = true
                            btn.setTextColor(resources.getColor(android.R.color.black))
                        }
                    }
                }
                (view.findViewById<View>(R.id.backView)).setOnClickListener {
                    if (questionNumber > 0) {
                        isAnswer = false
                        btnNext.text = getString(R.string.to_answer)
                        questionNumber--
                        editView(
                            listQuestions,
                            questionNumber,
                            imageView,
                            textQuestion,
                            questionNumberTextView,
                            listRadioButton,
                            btnNext,
                            radioGroup
                        )
                        for (btn in listRadioButton) {
                            btn.isClickable = true
                            btn.setTextColor(resources.getColor(android.R.color.black))
                        }
                    }
                }
                btnNext.setOnClickListener {
                    if (isAnswer) {
                        if (questionNumber < listQuestions.size - 1) {
                            isAnswer = false
                            btnNext.text = getString(R.string.to_answer)
                            questionNumber++
                            editView(
                                listQuestions,
                                questionNumber,
                                imageView,
                                textQuestion,
                                questionNumberTextView,
                                listRadioButton,
                                btnNext,
                                radioGroup
                            )
                            for (btn in listRadioButton) {
                                btn.isClickable = true
                                btn.setTextColor(resources.getColor(android.R.color.black))
                            }
                        }
                    } else {
                        isAnswer = true
                        btnNext.text = getString(R.string.next)
                        setAnswer(
                            listQuestions,
                            listRadioButton,
                            questionNumber,
                            radioGroup.indexOfChild(view.findViewById(radioGroup.checkedRadioButtonId)),
                            listCorrectAnswers
                        )
                        if (!listCorrectAnswers.contains(0)) {
                            listResult.adapter = ResultModuleAdapter(listCorrectAnswers, context!!)
                            view.findViewById<ConstraintLayout>(R.id.moduleQuestionConstraintLayout)
                                .visibility = View.GONE
                            view.findViewById<ConstraintLayout>(R.id.moduleQuestionResult)
                                .visibility = View.VISIBLE
                        }
                    }
                }
                super.onViewCreated(view, savedInstanceState)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun editView(
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
            .load("https://edu-pediatrics.com/storage/news/189/kafedra_360x250px.jpg") //Todo delete and use .load(list[questionNumber].imageUrl)
            .into(imageView)
        textQuestion.text = list[questionNumber].text
        for ((x, btn) in listRadioButton.withIndex()) {
            btn.text = list[questionNumber].answers[x].text
        }
        radioGroup.clearCheck()
        btnNext.isEnabled = false
        questionNumberTextView.text =
            (questionNumber + 1).toString() + " " + resources.getString(R.string.one_to_ten)
    }

    private fun setAnswer(
        listQuestions: List<Question>,
        listRadioButton: MutableList<RadioButton>,
        questionNumber: Int,
        selectedNumber: Int,
        listCorrectAnswers: MutableList<Int>
    ) {
        if (listQuestions[questionNumber].correctAnswersCombo[0] == selectedNumber) {
            listCorrectAnswers[questionNumber] = 1
            listRadioButton[selectedNumber].setTextColor(
                resources.getColor(
                    android.R.color.holo_green_dark
                )
            )
        } else {
            listCorrectAnswers[questionNumber] = -1
            listRadioButton[selectedNumber].setTextColor(
                resources.getColor(
                    android.R.color.holo_red_dark
                )
            )
            listRadioButton[listQuestions[questionNumber].correctAnswersCombo[0]].setTextColor(
                resources.getColor(android.R.color.holo_green_dark)
            )
        }
        for (btn in listRadioButton) {
            btn.isClickable = false
        }
    }
}