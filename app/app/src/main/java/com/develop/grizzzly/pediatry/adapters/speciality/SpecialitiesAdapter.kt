package com.develop.grizzzly.pediatry.adapters.speciality

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.databinding.SpecialityItemBinding
import com.develop.grizzzly.pediatry.network.model.Speciality
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationViewModel
import com.develop.grizzzly.pediatry.viewmodel.speciality.SpecialityItemViewModel

class SpecialitiesAdapter constructor(val activity : Activity, val model : RegistrationViewModel, val type : Int) : PagedListAdapter<Speciality, SpecialitiesAdapter.SpecialityViewHolder>(SpecialitiesDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SpecialityItemBinding.inflate(inflater, parent, false)
        return SpecialityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecialityViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(activity, model, it, type) }
    }


    class SpecialityViewHolder(private val binding: SpecialityItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(activity : Activity, model : RegistrationViewModel, speciality: Speciality, type : Int) {
            val viewModel = SpecialityItemViewModel(model, activity, speciality, type)
            binding.model = viewModel
        }


    }

}