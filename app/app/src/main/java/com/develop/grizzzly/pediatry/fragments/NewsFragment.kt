package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.adapters.news.NewsAdapter
import com.develop.grizzzly.pediatry.viewmodel.news.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : Fragment() {


    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myActivity = activity as MainActivity?
        myActivity?.supportActionBar?.show()
        myActivity?.bottom_nav?.visibility = View.VISIBLE

        return inflater.inflate(R.layout.fragment_news, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)

        adapter = NewsAdapter()
        listNews.adapter = adapter
        listNews.layoutManager = LinearLayoutManager(activity)
        //val manager = GridLayoutManager(activity ,2)
        //listNews.layoutManager = manager

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
