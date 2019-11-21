package com.develop.grizzzly.pediatry.viewmodel.module

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.fragments.ModulePostFragmentDirections

class ModulePostViewModel : ViewModel() {

    var id: Long = 0

    fun onTest(@Suppress("UNUSED_PARAMETER") view: View) {
        val transition = ModulePostFragmentDirections.actionModuleToQuestions()
        transition.moduleId = id.toInt()
        Navigation.findNavController(view).navigateNoExcept(transition)
    }
}