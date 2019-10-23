package com.develop.grizzzly.pediatry.viewmodel.menu

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.images.glideLocal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MenuItemViewModel(val menuItem: MenuItem) : ViewModel() {

    fun onMenuItem(view: View) {
        viewModelScope.launch {
            if (menuItem.direction != null) {
                delay(100)
                Navigation.findNavController(view)
                    .navigateNoExcept(menuItem.direction)
            }
        }
    }

    companion object {
        @BindingAdapter("resource")
        @JvmStatic
        fun loadImage(view: ImageView, resource: Int?) {
            resource?.let { _ ->
                glideLocal(view, resource)
            }
        }
    }

}