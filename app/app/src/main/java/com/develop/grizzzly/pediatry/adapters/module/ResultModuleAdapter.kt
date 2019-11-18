package com.develop.grizzzly.pediatry.adapters.module

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.R

class ResultModuleAdapter(private val results: MutableList<Int>, val context: Context) :
    RecyclerView.Adapter<ResultModuleAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.result_module_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.numText.text = "${(position + 1)})"
        holder.resultText.text = results[position].toString()
        if (results[position] == 1)
            holder.constraintLayout.background =
                context.getDrawable(R.drawable.result_item_green_bg)
        else
            holder.constraintLayout.background = context.getDrawable(R.drawable.result_item_red_bg)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resultText = view.findViewById<TextView>(R.id.resultText)!!
        val numText = view.findViewById<TextView>(R.id.num)!!
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.resultModuleBackground)!!
    }
}