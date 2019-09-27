package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.util.Log
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.adapters.webinar.WebinarAdapter
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.viewmodel.webinar.WebinarViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_webinars.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WebinarsFragment : Fragment(){
    private lateinit var adapter: WebinarAdapter
    private lateinit var viewModel: WebinarViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.toolbarTitle?.text = "Конференции"
        return inflater.inflate(R.layout.fragment_webinars, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this).get(WebinarViewModel::class.java)

        listWebinars.setHasFixedSize(true)

        adapter = WebinarAdapter()
        listWebinars.adapter = adapter

        val manager = GridLayoutManager(activity ,2)
        listWebinars.layoutManager = manager

        viewModel.conferenceLiveData.observe(this, Observer {
            adapter.submitList(it)
            refreshLayout.isRefreshing = false
        })

        refreshLayout.setOnRefreshListener {
            viewModel.dataSourceFactory.postLiveData?.value?.invalidate()
        }

        super.onViewCreated(view, savedInstanceState)
    }

}