package com.develop.grizzzly.pediatry.adapters.news

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.*
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.BR

import com.develop.grizzzly.pediatry.databinding.NewsItemBinding
import com.develop.grizzzly.pediatry.network.model.News
import com.develop.grizzzly.pediatry.viewmodel.news.NewsItemViewModel
import kotlinx.android.synthetic.main.news_item.view.*


class NewsAdapter : PagedListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    class NewsViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(news: News) {
            val viewModel = NewsItemViewModel(news)
            binding.model = viewModel
        }


    }

}