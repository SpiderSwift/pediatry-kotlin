package com.develop.grizzzly.pediatry.fragments

import android.content.pm.ActivityInfo
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
import com.develop.grizzzly.pediatry.images.ImageAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.ModulePost
import com.develop.grizzzly.pediatry.viewmodel.module.ModulePostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModulePostFragment : Fragment() {

    private lateinit var viewModel: ModulePostViewModel

    private val args: ModulePostFragmentArgs by navArgs()

    override fun onCreateView( //todo изменить картинку load and create books
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        var activeSlide = 0
        val activity = activity as? MainActivity
        val picasso = ImageAccess.picasso
        activity?.supportActionBar?.hide()
        activity?.toolbarTitle?.text = "Модуль"
        activity?.bottom_nav?.visibility = View.GONE
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
            var module: ModulePost? = null
            try {
                module = WebAccess.pediatryApi.getModuleById(viewModel.id).body()?.response
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (module != null || module?.slides!!.isNotEmpty())
                withContext(Dispatchers.Main) {
                    binding.moduleNum.text = getString(R.string.module_is, module.number)
                    binding.tvTitle.text = module.title
                    picasso.load(module.slides[activeSlide].image).placeholder(R.drawable.loading)
                        .into(binding.moduleImage)
                    binding.nextView.setOnClickListener {
                        activeSlide++
                        if (activeSlide > module.slides.size - 1) activeSlide = 0
                        picasso.load(module.slides[activeSlide].image).fit()
                            .placeholder(R.drawable.loading).into(binding.moduleImage)
                    }
                    binding.backView.setOnClickListener {
                        activeSlide--
                        if (activeSlide < 0) activeSlide = module.slides.size - 1
                        picasso.load(module.slides[activeSlide].image).fit()
                            .placeholder(R.drawable.loading).into(binding.moduleImage)
                    }
                }
        }
        return binding.root
    }
}