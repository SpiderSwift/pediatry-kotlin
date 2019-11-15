package com.develop.grizzzly.pediatry.adapters.module

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.databinding.ModuleItemBinding
import com.develop.grizzzly.pediatry.network.model.Module
import com.develop.grizzzly.pediatry.viewmodel.module.ModuleItemViewModel

class ModuleAdapter :
    PagedListAdapter<Module, ModuleAdapter.ModuleViewHolder>(ModuleDiffUtilCallBack()) {
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
            binding.textView2.text = module.id.toString()
            binding.text.text = module.title
            binding.model = ModuleItemViewModel(module)
        }
    }
}