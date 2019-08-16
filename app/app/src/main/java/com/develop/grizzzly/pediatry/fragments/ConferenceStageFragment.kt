package com.develop.grizzzly.pediatry.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.adapters.conference.ConferenceStageAdapter
import com.develop.grizzzly.pediatry.network.model.Program
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_conference_stage.*

class ConferenceStageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myActivity = activity as MainActivity?
        myActivity?.supportActionBar?.show()
        myActivity?.bottom_nav?.visibility = View.VISIBLE
        myActivity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        myActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        return inflater.inflate(R.layout.fragment_conference_stage, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val llm = LinearLayoutManager(activity)
        listStages.layoutManager = llm
        listStages.adapter = ConferenceStageAdapter(listOf(
            Program("9:00-9:15","Лекция с разбором клинических случаев (кейс-метод) «Питание здорового ребенка».","И.Н. Захарова, д.м.н., профессор, зав. кафедрой педиатрии ФГБОУ ДПО РМАНПО Минздрава России, г.Москва"),
            Program("9:15-9:30","Лекция: «Инфекции и иммунитет: новый взгляд на старую проблему».","И.Н. Захарова, д.м.н., профессор, зав. кафедрой педиатрии ФГБОУ ДПО РМАНПО Минздрава России, г.Москва"),
            Program("9:30-10:15","Дискуссия.",""),
            Program("10:15-11:15","Перерыв. Кофе-брейк.",""),
            Program("10:15-11:15","Перерыв. Кофе-брейк.",""),
            Program("10:15-11:15","Перерыв. Кофе-брейк.","")
            )
        )
        super.onViewCreated(view, savedInstanceState)
    }
}