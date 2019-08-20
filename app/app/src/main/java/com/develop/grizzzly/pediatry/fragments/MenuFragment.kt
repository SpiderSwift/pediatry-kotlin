package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.util.Log
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
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.viewmodel.menu.MenuItem
import com.develop.grizzzly.pediatry.viewmodel.menu.MenuViewModel
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentMenuBinding>(
            inflater,
            R.layout.fragment_menu,
            container,
            false
        )

        val model = activity?.run {
            ViewModelProviders.of(this).get(MenuViewModel::class.java)
        }!!

        binding.model = model
        binding.lifecycleOwner = this

//        GlobalScope.launch {
//            val response = WebAccess.pediatryApi.getProfile()
//            if (response.isSuccessful) {
//                val name = response.body()?.response?.name
//                val lastname = response.body()?.response?.lastname
//                val avatarUrl = "${response.body()?.response?.avatar}"
//                withContext(Dispatchers.Main) {
//                    model.name.postValue(name)
//                    model.lastname.postValue(lastname)
//                    model.avatarUrl.postValue(avatarUrl)
//                }
//            }
//        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val llm = LinearLayoutManager(activity)
        menuList.layoutManager = llm
        menuList.adapter = MenuAdapter()

        super.onViewCreated(view, savedInstanceState)
    }
}