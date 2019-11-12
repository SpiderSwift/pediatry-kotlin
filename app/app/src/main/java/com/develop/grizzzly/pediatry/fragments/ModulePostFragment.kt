package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import androidx.lifecycle.ViewModelProvider
import com.develop.grizzzly.pediatry.databinding.FragmentModulePostBinding
import com.develop.grizzzly.pediatry.viewmodel.module.ModulePostViewModel
import kotlinx.android.synthetic.main.activity_main.*

class ModulePostFragment: Fragment() {

    private lateinit var viewModel: ModulePostViewModel

    private val args: ModulePostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as? MainActivity
        activity?.supportActionBar?.show()
        activity?.toolbarTitle?.text = "Модуль"
        activity?.bottom_nav?.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this).get(ModulePostViewModel::class.java)
        viewModel.id = args.moduleId
        val binding = DataBindingUtil.inflate<FragmentModulePostBinding>(
            inflater,
            R.layout.fragment_module_post,
            container,
            false
        )
        binding.model = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //todo
        super.onViewCreated(view, savedInstanceState)
    }

}