package com.develop.grizzzly.pediatry.adapters.news

import androidx.recyclerview.widget.DiffUtil
import com.develop.grizzzly.pediatry.network.model.News

class NewsDiffUtilCallBack : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return (oldItem == newItem)
    }

}