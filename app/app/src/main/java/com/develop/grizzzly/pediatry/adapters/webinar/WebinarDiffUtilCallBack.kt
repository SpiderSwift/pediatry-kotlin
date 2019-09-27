package com.develop.grizzzly.pediatry.adapters.webinar

import androidx.recyclerview.widget.DiffUtil
import com.develop.grizzzly.pediatry.network.model.Webinar

class WebinarDiffUtilCallBack : DiffUtil.ItemCallback<Webinar>() {
    override fun areItemsTheSame(oldItem: Webinar, newItem: Webinar): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Webinar, newItem: Webinar): Boolean {
        return (oldItem == newItem)
    }

}