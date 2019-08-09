package com.develop.grizzzly.pediatry.adapters.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.develop.grizzzly.pediatry.databinding.NewsItemBinding
import com.develop.grizzzly.pediatry.network.model.News
import com.develop.grizzzly.pediatry.viewmodel.news.NewsItemViewModel


class NewsAdapter : PagedListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, this, position)
        }
    }


    class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News, adapter: NewsAdapter, position: Int) {
            val data = MutableLiveData<News>(news)
            val viewModel = NewsItemViewModel(data, adapter, position)
            binding.model = viewModel
        }

    }

}