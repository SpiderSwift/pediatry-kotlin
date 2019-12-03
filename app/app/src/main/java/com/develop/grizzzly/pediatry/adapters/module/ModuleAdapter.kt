package com.develop.grizzzly.pediatry.adapters.module

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.R
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
            binding.textView2.text =
                binding.root.context.getString(R.string.module_is, module.number)
            binding.text.text = module.title
            binding.model = ModuleItemViewModel(module)
            binding.testStatus.text =
                when (module.testStatus) {
                    Module.noTesting -> "Тест еще не был пройден"
                    Module.correctTesting -> "Успешно пройденный тест"
                    Module.incorrectTesting -> "Неудачно пройденный тест"
                    else -> "Что-то пошло не так"
                }
        }
    }
}