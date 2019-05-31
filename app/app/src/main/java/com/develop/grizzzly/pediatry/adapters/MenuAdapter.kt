package com.develop.grizzzly.pediatry.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.models.NavigationItem
import kotlinx.android.synthetic.main.menu_item.view.*

/**Adapter for menu navigation items**/

class MenuAdapter(
    private val dataset: ArrayList<NavigationItem>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<MenuAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val container = view.menu_item_container
        val title = view.menu_item_text
        val image = view.menu_item_image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false))


    override fun getItemCount(): Int  = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            holder.title?.text =  dataset[position].title
            holder.image?.setImageResource(dataset[position].img)

            holder.container?.setOnClickListener{view -> listener.onClick(view, position)}
        }
    }
}

