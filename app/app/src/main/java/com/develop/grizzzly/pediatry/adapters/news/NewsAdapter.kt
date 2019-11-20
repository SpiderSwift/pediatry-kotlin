package com.develop.grizzzly.pediatry.adapters.news

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.NewsItemBinding
import com.develop.grizzzly.pediatry.images.glideLocal
import com.develop.grizzzly.pediatry.network.WebAccess
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
        getItem(position)?.let { holder.bind(it, this, position) }
    }

    class NewsViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News, adapter: NewsAdapter, position: Int) {
            if (news.isAd) {
                binding.adImage.visibility = View.VISIBLE
                binding.root.newsCard.visibility = View.GONE
                binding.root.adCard.visibility = View.VISIBLE
                if (news.adVideoUrl != null && news.adVideoUrl.isNotEmpty()) {
                    binding.adVideo.setVideoURI(Uri.parse(news.adVideoUrl))
                    binding.adVideo.start()
                    binding.adCard.setOnClickListener {
                        if (binding.adVideo.isPlaying)
                            binding.adVideo.pause()
                        else binding.adVideo.start()
                    }
                    binding.adVideo.setOnPreparedListener {
                        binding.adImage.visibility = View.GONE
                    }
                    binding.adVideo.setOnCompletionListener {
                        //Todo начинаем крутить видео снова?
                        // Показываем превью?
                        // Или просто чёрный экран?
                    }
                } else {
                    binding.adVideo.visibility = View.INVISIBLE
                }
            } else {
                binding.root.adCard.visibility = View.GONE
                binding.root.newsCard.visibility = View.VISIBLE
            }
            glideLocal(
                binding.root.ivLike,
                if (news.likedByUsers.contains(WebAccess.token().id))
                    R.drawable.ic_heart else R.drawable.ic_unlike
            )
            binding.model = NewsItemViewModel(news, adapter, position)
        }
    }
}