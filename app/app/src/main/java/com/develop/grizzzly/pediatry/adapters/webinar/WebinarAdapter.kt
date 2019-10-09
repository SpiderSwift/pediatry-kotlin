package com.develop.grizzzly.pediatry.adapters.webinar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.databinding.WebinarItemBinding
import com.develop.grizzzly.pediatry.network.model.Webinar
import com.develop.grizzzly.pediatry.viewmodel.webinar.WebinarItemViewModel

class WebinarAdapter :
    PagedListAdapter<Webinar, WebinarAdapter.WebinarViewHolder>(WebinarDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebinarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WebinarItemBinding.inflate(inflater, parent, false)
        return WebinarViewHolder(binding)
    }


    override fun onBindViewHolder(holder: WebinarViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


    class WebinarViewHolder(val binding: WebinarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(webinar: Webinar) {
            val data = MutableLiveData<Webinar>(webinar)
            val viewModel = WebinarItemViewModel(data)
            binding.model = viewModel
        }

    }

}