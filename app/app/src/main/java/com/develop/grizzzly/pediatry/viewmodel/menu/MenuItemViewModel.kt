package com.develop.grizzzly.pediatry.viewmodel.menu

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MenuItemViewModel(val menuItem: MenuItem) : ViewModel() {

    fun onMenuItem(view: View) {
        viewModelScope.launch {
            menuItem.direction?.let {
                delay(100)
                Navigation.findNavController(view)
                    .navigateNoExcept(it)
            }
        }
    }

}