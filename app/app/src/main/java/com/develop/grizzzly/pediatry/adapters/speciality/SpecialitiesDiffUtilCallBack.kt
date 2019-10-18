package com.develop.grizzzly.pediatry.adapters.speciality

import androidx.recyclerview.widget.DiffUtil
import com.develop.grizzzly.pediatry.network.model.Speciality

class SpecialitiesDiffUtilCallBack : DiffUtil.ItemCallback<Speciality>() {

    override fun areItemsTheSame(oldItem: Speciality, newItem: Speciality): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Speciality, newItem: Speciality): Boolean = oldItem == newItem

}