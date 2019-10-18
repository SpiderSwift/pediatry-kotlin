package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.databinding.FragmentWebinarInfoBinding
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.util.showToast
import com.develop.grizzzly.pediatry.viewmodel.webinar.WebinarPostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_webinar_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WebinarInfoFragment : Fragment() {

    private lateinit var viewModel: WebinarPostViewModel

    private val args: WebinarInfoFragmentArgs by navArgs()

    private var registered: Boolean = false
    private var canWatch: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as? MainActivity
        mainActivity?.supportActionBar?.hide()
        viewModel = ViewModelProviders.of(this).get(WebinarPostViewModel::class.java)
        mainActivity?.toolbarTitle?.text = "Вебинар"
        mainActivity?.bottom_nav?.visibility = View.GONE
        setHasOptionsMenu(true)
        //viewModel = ViewModelProviders.of(this).get(WebinarInfoViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentWebinarInfoBinding>(
            inflater,
            R.layout.fragment_webinar_info,
            container,
            false
        )
        binding.model = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainActivity = activity as? MainActivity
        btnPart.setOnClickListener {
            if (canWatch) {
                // TODO: добавить переход к просмотру видео
                return@setOnClickListener
            }
            GlobalScope.launch {
                try {
                    val response =
                        if (!registered) WebAccess.pediatryApi.registerForWebinar(args.id)
                        else WebAccess.pediatryApi.unregisterForWebinar(args.id)
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            if (!registered) {
                                btnPart.text = "Отменить"
                                registered = true
                            } else {
                                btnPart.text = "Участвовать"
                                registered = false
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        close.setOnClickListener {
            activity?.onBackPressed()
        }

        ask.setOnClickListener {
            GlobalScope.launch {
                if (teQuestion.text.toString().isNotEmpty()) {
                    try {
                        WebAccess.pediatryApi.sendMessageForWebinar(
                            args.id,
                            teQuestion.text.toString()
                        )
                        withContext(Dispatchers.Main) {
                            sendField.visibility = View.GONE
                            toast("Сообщение отправлено")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            toast("Ошибка при отправке")
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        toast("Введите сообщение")
                    }
                }
            }
        }

        btnAsk.setOnClickListener {
            sendField.visibility = View.VISIBLE
            teQuestion.requestFocus()
        }

        GlobalScope.launch {
            try {
                val response = WebAccess.pediatryApi.getWebinar(args.id)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        val window = mainActivity?.window
                        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window?.statusBarColor =
                            mainActivity?.resources?.getColor(R.color.colorAccent) ?: 0
                        val web = response.body()!!.response!!
                        viewModel.data.value = web
                        mainContent.visibility = View.VISIBLE
                        load.visibility = View.GONE
                        tvDate.text = "${viewModel.getTwoTimeDate()} ${viewModel.getMonth()}"
                        registered = web.isRegistered
                        Log.d("TAG", "code ${web.youtubeCode}")
                        if ((web.status != 2L) or (web.status != 3L)) {
                            canWatch = false
                            if (web.isRegistered) {
                                btnPart.text = "Отменить"
                            } else {
                                btnPart.text = "Участвовать"
                            }
                        } else {
                            canWatch = true
                            btnPart.text = "Перейти к просмотру"
                        }
                        tvText.text = viewModel.getDetailDescription()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        load.visibility = View.GONE
                        errorMsg.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    load.visibility = View.GONE
                    errorMsg.visibility = View.VISIBLE
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun toast(msg: String) {
        showToast(activity!!, R.layout.custom_toast, msg)
    }

}