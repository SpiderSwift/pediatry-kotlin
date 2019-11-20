package com.develop.grizzzly.pediatry.viewmodel.module

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.fragments.ModuleFragmentDirections
import com.develop.grizzzly.pediatry.network.model.Module

class ModuleItemViewModel(val module: Module) : ViewModel() {

    fun onModule(view: View) {
        val transition = ModuleFragmentDirections.actionNewsToModulePost()
        transition.moduleId = module.id
        Navigation.findNavController(view).navigateNoExcept(transition)
    }
}