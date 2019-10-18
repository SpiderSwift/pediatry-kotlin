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
import com.develop.grizzzly.pediatry.images.glideLocal
import com.develop.grizzzly.pediatry.viewmodel.news.NewsItemViewModel
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter : PagedListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, this, position) }
    }

    class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News, adapter: NewsAdapter, position: Int) {
            binding.root.adCard.visibility = if (news.isAd) View.VISIBLE else View.GONE
            binding.root.newsCard.visibility = if (news.isAd) View.GONE else View.VISIBLE
            glideLocal(
                binding.root.ivLike,
                if (news.likedByUsers.contains(WebAccess.token().id))
                    R.drawable.ic_heart else R.drawable.ic_unlike
            )
            binding.model = NewsItemViewModel(news, adapter, position)
        }

    }

}