package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.adapters.conference.ConferenceAdapter
import com.develop.grizzzly.pediatry.viewmodel.conference.ConferenceViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_conferentions.*

class ConferentionsFragment : Fragment() {

    private lateinit var adapter: ConferenceAdapter
    private lateinit var viewModel: ConferenceViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbarTitle?.text = "Конференции"
        return inflater.inflate(R.layout.fragment_conferentions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ConferenceViewModel::class.java)
        listConference.setHasFixedSize(true)
        adapter = ConferenceAdapter()
        listConference.adapter = adapter
        val manager = GridLayoutManager(activity, 2)
        listConference.layoutManager = manager
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