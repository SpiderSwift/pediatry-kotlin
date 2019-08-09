package com.develop.grizzzly.pediatry.adapters.conference

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.databinding.ConferenceItemBinding
import com.develop.grizzzly.pediatry.network.model.Conference
import com.develop.grizzzly.pediatry.viewmodel.conference.ConferenceItemViewModel

class ConferenceAdapter : PagedListAdapter<Conference, ConferenceAdapter.ConferenceViewHolder>(ConferenceDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConferenceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ConferenceItemBinding.inflate(inflater, parent, false)
        return ConferenceViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ConferenceViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


    class ConferenceViewHolder(val binding: ConferenceItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(conference: Conference) {
            val data = MutableLiveData<Conference>(conference)
            val viewModel = ConferenceItemViewModel(data)
            binding.model = viewModel
        }

    }

}