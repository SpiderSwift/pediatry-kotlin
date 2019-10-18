package com.develop.grizzzly.pediatry.adapters.conference

import androidx.recyclerview.widget.DiffUtil
import com.develop.grizzzly.pediatry.network.model.Conference

class ConferenceDiffUtilCallBack : DiffUtil.ItemCallback<Conference>() {

    override fun areItemsTheSame(oldItem: Conference, newItem: Conference): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Conference, newItem: Conference): Boolean = oldItem == newItem

}