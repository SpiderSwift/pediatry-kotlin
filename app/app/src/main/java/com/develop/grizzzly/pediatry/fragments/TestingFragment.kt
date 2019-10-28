package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.databinding.FragmentDoctorsTestingBinding
import com.develop.grizzzly.pediatry.viewmodel.practicetest.TestingViewModel
import kotlinx.android.synthetic.main.activity_main.*

class TestingFragment : Fragment() {

    //private lateinit var adapter: WebinarAdapter
    //private lateinit var viewModel: WebinarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDoctorsTestingBinding>(
            inflater,
            R.layout.fragment_doctors_testing,
            container,
            false
        )
        val model = ViewModelProvider(this).get(TestingViewModel::class.java)
        binding.model = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainActivity = activity as? MainActivity
        activity?.toolbarTitle?.visibility = View.GONE
        mainActivity?.supportActionBar?.hide()
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.statusBarColor = activity?.resources?.getColor(android.R.color.white, null) ?: 0
        activity?.bottom_nav?.visibility = View.GONE
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