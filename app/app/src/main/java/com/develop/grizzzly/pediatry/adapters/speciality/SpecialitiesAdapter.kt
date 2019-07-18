package com.develop.grizzzly.pediatry.adapters.speciality

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.network.model.Speciality
import com.develop.grizzzly.pediatry.databinding.SpecialityItemBinding
import com.develop.grizzzly.pediatry.viewmodel.speciality.SpecialityItemViewModel

class SpecialitiesAdapter : PagedListAdapter<Speciality, SpecialitiesAdapter.SpecialityViewHolder>(SpecialitiesDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SpecialityItemBinding.inflate(inflater, parent, false)
        return SpecialityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecialityViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    class SpecialityViewHolder(private val binding: SpecialityItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(speciality: Speciality) {
            val viewModel = SpecialityItemViewModel(speciality)
            binding.model = viewModel
        }


    }

}