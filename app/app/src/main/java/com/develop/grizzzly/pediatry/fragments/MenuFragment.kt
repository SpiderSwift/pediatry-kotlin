package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.adapters.menu.MenuAdapter
import com.develop.grizzzly.pediatry.databinding.FragmentMenuBinding
import com.develop.grizzzly.pediatry.viewmodel.menu.MenuViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentMenuBinding>(
            inflater,
            R.layout.fragment_menu,
            container,
            false
        )
        val mainActivity = activity
        mainActivity?.toolbarTitle?.text = "Меню"

        val model = activity?.run {
            ViewModelProviders.of(this).get(MenuViewModel::class.java)
        }!!

        binding.model = model
        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val llm = LinearLayoutManager(activity)
        menuList.layoutManager = llm
        menuList.adapter = MenuAdapter()
        menuList.itemAnimator = null

        super.onViewCreated(view, savedInstanceState)
    }
}