package com.develop.grizzzly.pediatry.viewmodel.module

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.fragments.ModuleFragmentDirections
import com.develop.grizzzly.pediatry.fragments.ModulePostFragmentDirections
import com.develop.grizzzly.pediatry.network.WebAccess
import kotlinx.coroutines.launch

class ModulePostViewModel : ViewModel() { //todo

    var id: Long = 0

    fun onTest(@Suppress("UNUSED_PARAMETER") view: View) {
        val bib = ModulePostFragmentDirections.actionModuleToQuestions()
        bib.moduleId = id.toInt()
        Navigation.findNavController(view).navigateNoExcept(bib)
    }

    fun onBook(view: View) {}
}