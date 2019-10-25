package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.databinding.FragmentDoctorsTestingQuestionsBinding
import com.develop.grizzzly.pediatry.viewmodel.practicetest.TestingQuestionsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


class TestingsQuestionsFragment : Fragment() {

//private lateinit var adapter: WebinarAdapter
    //private lateinit var viewModel: WebinarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDoctorsTestingQuestionsBinding>(
            inflater,
            R.layout.fragment_doctors_testing_questions,
            container,
            false
        )
        val model = ViewModelProvider(this).get(TestingQuestionsViewModel::class.java)
        binding.model = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageView = view.findViewById<ImageView>(R.id.testing_image)
        Picasso.get()
            .load("https://edu-pediatrics.com/storage/news/188/Nestle_Ukraintsev3_360x250px.jpg")
            .into(imageView)
        val mainActivity = activity as? MainActivity
        activity?.toolbarTitle?.visibility = View.GONE
        mainActivity?.supportActionBar?.hide()
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.statusBarColor = activity?.resources?.getColor(android.R.color.white, null) ?: 0
        activity?.bottom_nav?.visibility = View.GONE
        val radioGroup = view.findViewById(R.id.radioGroup) as RadioGroup
        val radioButton1 = RadioButton(context)
        radioButton1.setText("Ответ 1")

        val radioButton2 = RadioButton(context)
        radioButton2.setText("Ответ 2")

        val radioButton3 = RadioButton(context)
        radioButton3.setText("Ответ 3")

        val radioButton4 = RadioButton(context)
        radioButton4.setText("Ответ 4")

        radioGroup.addView(radioButton1)
        radioGroup.addView(radioButton2)
        radioGroup.addView(radioButton3)
        radioGroup.addView(radioButton4)

        radioGroup.setOnCheckedChangeListener { _: RadioGroup, number: Int ->
            Log.println(Log.ASSERT, "msg", "$number")
        }

        //viewModel = ViewModelProvider(this).get(WebinarViewModel::class.java)
        // listWebinars.setHasFixedSize(true)
        // adapter = WebinarAdapter()
        // listWebinars.adapter = adapter
        //  listWebinars.layoutManager = GridLayoutManager(activity, 2)
        //viewModel.conferenceLiveData.observe(this, Observer {
        //    adapter.submitList(it)
        //    refreshLayout.isRefreshing = false
        //  })
        //  refreshLayout.setOnRefreshListener {
        //     viewModel.dataSourceFactory.postLiveData?.value?.invalidate()
        //  }
        super.onViewCreated(view, savedInstanceState)
    }
}