package com.develop.grizzzly.pediatry.adapters.conference

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.databinding.ConferenceStageItemBinding
import com.develop.grizzzly.pediatry.network.model.Program

import com.develop.grizzzly.pediatry.viewmodel.conference.ConferenceStageItemViewModel

class ConferenceStageAdapter(private val list: List<Program>) :
    RecyclerView.Adapter<ConferenceStageAdapter.ConferenceStageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConferenceStageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ConferenceStageItemBinding.inflate(inflater, parent, false)
        return ConferenceStageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ConferenceStageViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ConferenceStageViewHolder(val binding: ConferenceStageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(program: Program) {
            val data = MutableLiveData<Program>(program)
            val viewModel = ConferenceStageItemViewModel(data)
            binding.model = viewModel
        }
    }
}