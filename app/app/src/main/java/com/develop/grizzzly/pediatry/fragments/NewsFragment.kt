package com.develop.grizzzly.pediatry.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.adapters.news.NewsAdapter
import com.develop.grizzzly.pediatry.viewmodel.news.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : Fragment() {

    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myActivity = activity as MainActivity?
        myActivity?.supportActionBar?.show()
        myActivity?.toolbarTitle?.text = "Новости"
        myActivity?.bottom_nav?.visibility = View.VISIBLE
        myActivity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        myActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        retainInstance = true
        return inflater.inflate(R.layout.fragment_news, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(NewsViewModel::class.java)
        }!!
        listNews.setHasFixedSize(true)

        if (viewModel.adapter == null) {
            viewModel.adapter = NewsAdapter()
        }

        adapter = viewModel.adapter!!
        listNews.adapter = adapter

        val llm = LinearLayoutManager(activity)
        llm.isAutoMeasureEnabled = false
        listNews.layoutManager = llm
        (listNews.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        viewModel.newsLiveData.observe(this, Observer {
            adapter.submitList(it)
            refreshLayout.isRefreshing = false
        })

        refreshLayout.setOnRefreshListener {
            viewModel.dataSourceFactory.postLiveData?.value?.invalidate()
        }


        super.onViewCreated(view, savedInstanceState)
    }


}
