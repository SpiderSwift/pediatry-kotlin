package com.develop.grizzzly.pediatry.adapters.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.R

import com.develop.grizzzly.pediatry.databinding.NewsItemBinding
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.News
import com.develop.grizzzly.pediatry.util.setImageGlide
import com.develop.grizzzly.pediatry.viewmodel.news.NewsItemViewModel
import kotlinx.android.synthetic.main.news_item.view.*


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
            //val data = MutableLiveData<News>(news)

            if (news.isAd) {
                binding.root.adCard.visibility = View.VISIBLE
                binding.root.newsCard.visibility = View.GONE
            } else {
                binding.root.newsCard.visibility = View.VISIBLE
                binding.root.adCard.visibility = View.GONE
            }

            if (news.likedByUsers.contains(WebAccess.id)) {
                setImageGlide("error", binding.root.ivLike, R.drawable.ic_heart)
            } else {
                setImageGlide("error", binding.root.ivLike, R.drawable.ic_unlike)
            }
            val viewModel = NewsItemViewModel(news, adapter, position)
            binding.model = viewModel
        }

    }

}