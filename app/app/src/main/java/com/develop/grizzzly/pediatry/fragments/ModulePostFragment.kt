package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.databinding.FragmentModulePostBinding
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.viewmodel.module.ModulePostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModulePostFragment : Fragment() {

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
        viewModel.id = args.moduleId.toLong()
        val binding = DataBindingUtil.inflate<FragmentModulePostBinding>(
            inflater,
            R.layout.fragment_module_post,
            container,
            false
        )
        binding.model = viewModel
        binding.lifecycleOwner = this
        GlobalScope.launch {
            val module = WebAccess.pediatryApi.getModuleById(viewModel.id).body()?.response
            withContext(Dispatchers.Main) {
                binding.test1.text = module!!.id.toString()
                binding.test2.text = module.title
                binding.test3.text = module.number.toString()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //todo
        super.onViewCreated(view, savedInstanceState)
    }


}