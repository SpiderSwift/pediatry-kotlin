package com.develop.grizzzly.pediatry.adapters.module

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.develop.grizzzly.pediatry.fragments.SlidePagerFragment
import com.develop.grizzzly.pediatry.viewmodel.module.ModulePostViewModel

class SlidePagerAdapter(
    fm: FragmentManager,
    private val listSlidesFragment: List<SlidePagerFragment>
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = ModulePostViewModel.listSlides.size
    override fun getItem(position: Int): Fragment = listSlidesFragment[position]
}