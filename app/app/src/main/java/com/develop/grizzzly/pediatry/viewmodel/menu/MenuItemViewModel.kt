package com.develop.grizzzly.pediatry.viewmodel.menu

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.util.setImageGlide
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MenuItemViewModel(val menuItem: MenuItem) : ViewModel() {


    fun onMenuItem(view : View) {
        viewModelScope.launch {
            if (menuItem.direction != null) {
                val navController = Navigation.findNavController(view)
                delay(100)
                navController.navigate(menuItem.direction)
            }
        }

    }


    companion object {
        @BindingAdapter("bind:resource")
        @JvmStatic
        fun loadImage(view: ImageView, resource: Int?) {
            resource?.let { it -> setImageGlide(it.toString(), view, resource) }
        }
    }
}