package com.develop.grizzzly.pediatry.viewmodel.module

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.fragments.ModulePostFragmentDirections

class ModulePostViewModel : ViewModel() {

    var id: Long = 0

    fun onTest(@Suppress("UNUSED_PARAMETER") view: View) {
        if ((view.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null) {
            val transition = ModulePostFragmentDirections.actionModuleToQuestions()
            transition.moduleId = id.toInt()
            Navigation.findNavController(view).navigateNoExcept(transition)
        } else Toast.makeText(view.context, "Ошибка сети!", Toast.LENGTH_LONG).show()
    }
}