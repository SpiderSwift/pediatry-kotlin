package com.develop.grizzzly.pediatry.adapters.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.MenuItemBinding
import com.develop.grizzzly.pediatry.viewmodel.menu.MenuItem
import com.develop.grizzzly.pediatry.viewmodel.menu.MenuItemViewModel

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val list: List<MenuItem> = listOf(
        MenuItem("Вебинары", R.drawable.ic_webinars, R.id.action_menu_to_webinars, 1f),
        //MenuItem("Трансляции", R.drawable.ic_translations, null, 0.3f),
        MenuItem("Тестирование", R.drawable.ic_testing, R.id.action_menu_to_testing, 1f),
        MenuItem("Модули", R.drawable.ic_testing, R.id.action_menu_to_modules, 1f),
        MenuItem("Разборы", R.drawable.ic_details, null, 0.3f),
        MenuItem("Поддержка", R.drawable.ic_support, null, 0.3f)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MenuItemBinding.inflate(inflater, parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class MenuViewHolder(val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: MenuItem) {
            binding.model = MenuItemViewModel(menuItem)
        }
    }

}