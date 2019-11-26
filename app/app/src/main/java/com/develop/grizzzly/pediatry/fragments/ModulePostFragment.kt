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
import kotlinx.android.synthetic.main.fragment_error.*
import kotlinx.android.synthetic.main.fragment_module_post.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModulePostFragment : Fragment() {

    private lateinit var viewModel: ModulePostViewModel

    private val args: ModulePostFragmentArgs by navArgs()

    private var isTooltips = true

    private var isVisibleTest = true

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
        val binding = DataBindingUtil.inflate<FragmentModulePostBinding>(
            inflater,
            R.layout.fragment_module_post,
            container,
            false
        )
        binding.model = viewModel
        viewModel.id = args.moduleId.toLong()
        binding.lifecycleOwner = this
        GlobalScope.launch {
            try {
                val module: ModulePost? =
                    WebAccess.pediatryApi.getModuleById(args.moduleId.toLong()).body()?.response
                withContext(Dispatchers.Main) {
                    cardView.setOnClickListener {
                        if (isTooltips) {
                            isTooltips = false
                            moduleNum.visibility = View.GONE
                            tvTitle.visibility = View.GONE
                            toTesting.visibility = View.GONE
                            nextView.visibility = View.GONE
                            backView.visibility = View.GONE
                        } else {
                            isTooltips = true
                            moduleNum.visibility = View.VISIBLE
                            tvTitle.visibility = View.VISIBLE
                            nextView.visibility = View.VISIBLE
                            backView.visibility = View.VISIBLE
                            toTesting.visibility = if (isVisibleTest) View.VISIBLE else View.GONE
                        }
                    }
                    progressBarView.visibility = View.GONE
                    border.visibility = View.VISIBLE
                    if (module!!.testStatus == 2 || module.testStatus == 3) {
                        isVisibleTest = false
                        toTesting.visibility = View.GONE
                    }
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
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBarView.visibility = View.GONE
                    includeError.visibility = View.VISIBLE
                    errorMsg.text = "Ошибка :("
                }
            }
        }
        return binding.root
    }
}