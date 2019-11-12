package com.develop.grizzzly.pediatry.viewmodel.module

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ModulePostViewModel : ViewModel() { //todo

    var id = 0

    fun onLike(@Suppress("UNUSED_PARAMETER") v: View) {
        viewModelScope.launch {

        }
    }

    fun onChoto(view: View) {}
}