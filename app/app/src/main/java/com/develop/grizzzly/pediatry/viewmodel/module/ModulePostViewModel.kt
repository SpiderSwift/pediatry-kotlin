package com.develop.grizzzly.pediatry.viewmodel.module

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.fragments.ModulePostFragmentDirections
import com.develop.grizzzly.pediatry.network.model.Slide

class ModulePostViewModel : ViewModel() {

    var isClicked: MutableLiveData<Boolean> = MutableLiveData(true)
    var id: Long = 0

    fun onTest(view: View) {
        val transition = ModulePostFragmentDirections.actionModuleToQuestions()
        transition.moduleId = id.toInt()
        Navigation.findNavController(view).navigateNoExcept(transition)
    }
    companion object {
        lateinit var viewModel: ModulePostViewModel //todo create Singleton?
        lateinit var listSlides: MutableList<Slide>
        var isTooltips = false
    }
}