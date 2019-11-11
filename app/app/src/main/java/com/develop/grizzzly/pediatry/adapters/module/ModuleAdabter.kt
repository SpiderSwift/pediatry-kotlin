package com.develop.grizzzly.pediatry.adapters.module

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.databinding.ModuleItemBinding
import com.develop.grizzzly.pediatry.network.model.Module
import com.develop.grizzzly.pediatry.viewmodel.module.ModuleItemViewModel

class ModuleAdabter :
    PagedListAdapter<Module, ModuleAdabter.ModuleViewHolder>(ModuleDiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ModuleItemBinding.inflate(inflater, parent, false)
        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ModuleViewHolder(val binding: ModuleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(module: Module) {
            val data = MutableLiveData<Module>(module)
            val viewModel = ModuleItemViewModel(data)
            binding.model = viewModel
        }
    }
}