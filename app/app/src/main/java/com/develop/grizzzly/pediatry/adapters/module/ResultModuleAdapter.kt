package com.develop.grizzzly.pediatry.adapters.module

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.R
import kotlinx.android.synthetic.main.result_module_item.view.*

class ResultModuleAdapter(private val results: MutableList<Int>, val context: Context) :
    RecyclerView.Adapter<ResultModuleAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.result_module_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.resultText.text = results[position].toString()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resultText = view.resultText!!
    }
}