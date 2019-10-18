package com.develop.grizzzly.pediatry.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import kotlinx.coroutines.*

class ConferenceStageFragment : Fragment() {

    private val args: ConferenceStageFragmentArgs by navArgs()
    private var data: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            try {
                val response = WebAccess.pediatryApi.getConference(args.id)
                if (response.isSuccessful) {
                    val registered = response.body()?.response?.isRegistered ?: false
                    val list = response.body()!!.response!!.programs
                    delay(150)
                    withContext(Dispatchers.Main) {
                        data.value = registered
                        listStages?.adapter = ConferenceStageAdapter(list)
                        load?.visibility = View.GONE
                        mainContent?.visibility = View.VISIBLE
                    }
                } else {
                    delay(200)
                    withContext(Dispatchers.Main) {
                        errorMsg?.visibility = View.VISIBLE
                        load?.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
                delay(200)
                withContext(Dispatchers.Main) {
                    errorMsg?.visibility = View.VISIBLE
                    load?.visibility = View.GONE
                }
            }
        }

        btnRegister.setOnClickListener {
            if (data.value == false) {
                GlobalScope.launch {
                    try {
                        val response = WebAccess.pediatryApi.registerForConference(args.id)
                        if (response.isSuccessful) {
                            withContext(Dispatchers.Main) {
                                data.value = true
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else {
                GlobalScope.launch {
                    try {
                        val response = WebAccess.pediatryApi.unregisterForConference(args.id)
                        if (response.isSuccessful) {
                            withContext(Dispatchers.Main) {
                                data.value = false
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
    
}