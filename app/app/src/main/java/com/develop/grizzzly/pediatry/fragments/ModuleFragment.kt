package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.adapters.webinar.WebinarAdapter
import com.develop.grizzzly.pediatry.viewmodel.webinar.WebinarViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_webinars.*

class ModuleFragment : Fragment() {

    private lateinit var adapter: WebinarAdapter
    private lateinit var viewModel: WebinarViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbarTitle?.text = getString(R.string.webinars)
        return inflater.inflate(R.layout.fragment_webinars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainActivity = activity as? MainActivity
        mainActivity?.supportActionBar?.show()
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.statusBarColor =
            activity?.resources?.getColor(android.R.color.white, null) ?: 0
        viewModel = ViewModelProvider(this).get(WebinarViewModel::class.java)
        listWebinars.setHasFixedSize(true)
        adapter = WebinarAdapter()
        listWebinars.adapter = adapter
        listWebinars.layoutManager = GridLayoutManager(activity, 2)
        viewModel.conferenceLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            refreshLayout.isRefreshing = false
        })
        refreshLayout.setOnRefreshListener {
            viewModel.dataSourceFactory.postLiveData?.value?.invalidate()
        }
        super.onViewCreated(view, savedInstanceState)
    }

}