package com.develop.grizzzly.pediatry.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.adapters.conference.ConferenceStageAdapter
import com.develop.grizzzly.pediatry.network.WebAccess
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_conference_stage.*
import kotlinx.android.synthetic.main.fragment_recovery_start.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConferenceStageFragment : Fragment() {

    private val args: ConferenceStageFragmentArgs by navArgs()
    private var data : MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myActivity = activity as MainActivity?
        myActivity?.toolbarTitle?.text = "Конференция"
        myActivity?.supportActionBar?.show()
        myActivity?.bottom_nav?.visibility = View.VISIBLE
        myActivity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        myActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        return inflater.inflate(R.layout.fragment_conference_stage, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val llm = LinearLayoutManager(activity)
        listStages.layoutManager = llm

        data.observe(this, Observer {
            if (it) {
                btnRegister.text = "Отменить заявку"
            } else {
                btnRegister.text = "Подать заявку"
            }
        })


        GlobalScope.launch {
            val response = WebAccess.pediatryApi.getConference(args.id)
            if (response.isSuccessful) {
                val registered = response.body()?.response?.isRegistered ?: false
                val list = response.body()!!.response!!.programs

                Log.d("TAG", list.toString())
                Log.d("TAG", registered.toString())
                withContext(Dispatchers.Main) {
                    data.value = registered
                    listStages.adapter = ConferenceStageAdapter(list)
                }

            } else {
                Log.d("TAG", response.errorBody()?.string())
            }

        }

        btnRegister.setOnClickListener {
            if (data.value == false) {
                GlobalScope.launch {
                    Log.d("TAG","REG")
                    val response = WebAccess.pediatryApi.registerForConference(args.id)
                    if (response.isSuccessful) {
                        Log.d("TAG", response.body()?.string())
                        withContext(Dispatchers.Main) {
                            data.value = true
                        }
                    } else {
                        Log.d("TAG", response.errorBody()?.string())
                    }
                }
            } else {
                GlobalScope.launch {
                    Log.d("TAG","UNNREG")
                    val response = WebAccess.pediatryApi.unregisterForConference(args.id)
                    if (response.isSuccessful) {
                        Log.d("TAG", response.body()?.string())
                        withContext(Dispatchers.Main) {
                            data.value = false
                        }
                    } else {
                        Log.d("TAG", response.errorBody()?.string())
                    }
                }
            }
        }
//        listStages.adapter = ConferenceStageAdapter(listOf(
//            Program("9:00-9:15","Лекция с разбором клинических случаев (кейс-метод) «Питание здорового ребенка».","И.Н. Захарова, д.м.н., профессор, зав. кафедрой педиатрии ФГБОУ ДПО РМАНПО Минздрава России, г.Москва"),
//            Program("9:15-9:30","Лекция: «Инфекции и иммунитет: новый взгляд на старую проблему».","И.Н. Захарова, д.м.н., профессор, зав. кафедрой педиатрии ФГБОУ ДПО РМАНПО Минздрава России, г.Москва"),
//            Program("9:30-10:15","Дискуссия.",""),
//            Program("10:15-11:15","Перерыв. Кофе-брейк.",""),
//            Program("10:15-11:15","Перерыв. Кофе-брейк.",""),
//            Program("10:15-11:15","Перерыв. Кофе-брейк.","")
//            )
//        )
        super.onViewCreated(view, savedInstanceState)
    }
}