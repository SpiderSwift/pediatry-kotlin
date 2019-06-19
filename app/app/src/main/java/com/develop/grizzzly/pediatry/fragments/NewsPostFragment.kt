package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.develop.grizzzly.pediatry.databinding.FragmentNewsPostBinding
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.viewmodel.news.NewsPostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news_post.*
import android.view.MenuInflater




class NewsPostFragment : Fragment() {


    private lateinit var viewModel: NewsPostViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mainActivity = activity as? MainActivity
        mainActivity?.supportActionBar?.show()
        mainActivity?.bottom_nav?.visibility = View.VISIBLE
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(NewsPostViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentNewsPostBinding>(
            inflater,
            R.layout.fragment_news_post,
            container,
            false
        )

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.action_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}