package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.images.glideRemoteWithAuth
import com.develop.grizzzly.pediatry.viewmodel.module.ModulePostViewModel

class SlidePagerFragment(private val position: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.slide_item, container, false)
        glideRemoteWithAuth(
            ModulePostViewModel.listSlides[position].image,
            view.findViewById(R.id.moduleImage),
            view.findViewById(R.id.progressBarModule)
        )
        view.findViewById<ImageView>(R.id.moduleImage).setOnClickListener {
            ModulePostViewModel.viewModel.isClicked.value = !ModulePostViewModel.isTooltips
        }
        return view
    }
}