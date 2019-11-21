package com.develop.grizzzly.pediatry.adapters.module

import androidx.recyclerview.widget.DiffUtil
import com.develop.grizzzly.pediatry.network.model.Module

class ModuleDiffUtilCallBack  : DiffUtil.ItemCallback<Module>() {

    override fun areItemsTheSame(oldItem: Module, newItem: Module): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Module, newItem: Module): Boolean = oldItem == newItem
}