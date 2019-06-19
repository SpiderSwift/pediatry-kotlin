package com.develop.grizzzly.pediatry.adapters.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.NewsItemBinding
import com.develop.grizzzly.pediatry.network.model.News
import com.develop.grizzzly.pediatry.viewmodel.news.NewsItemViewModel
import kotlinx.android.synthetic.main.news_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter: PagedListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)
        //val view = inflater.inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class NewsViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.model = NewsItemViewModel(MutableLiveData(news))

            //itemView.tvTitle.text = news.title
            //itemView.tvLike.text = news.liked.toString()
            //itemView.tvTime.setReferenceTime(news.date.time)
//            itemView.setOnClickListener {
//                val navController = findNavController(it)
//                navController.navigate(R.id.action_news_to_news_post)
//            }
        }
    }



}