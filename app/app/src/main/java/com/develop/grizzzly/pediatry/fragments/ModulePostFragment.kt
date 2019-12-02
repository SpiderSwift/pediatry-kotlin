package com.develop.grizzzly.pediatry.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.adapters.module.SlidePagerAdapter
import com.develop.grizzzly.pediatry.databinding.FragmentModulePostBinding
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.ModulePost
import com.develop.grizzzly.pediatry.util.ZoomPager
import com.develop.grizzzly.pediatry.viewmodel.module.ModulePostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_error.*
import kotlinx.android.synthetic.main.fragment_module_post.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ModulePostFragment : Fragment() {

    private val args: ModulePostFragmentArgs by navArgs()

    private var isVisibleTest = true

    override fun onCreateView( //todo изменить картинку load and create books
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val listSlidesFragment = mutableListOf<SlidePagerFragment>()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val activity = activity as? MainActivity
        activity?.supportActionBar?.hide()
        activity?.toolbarTitle?.text = "Модуль"
        activity?.bottom_nav?.visibility = View.GONE
        ModulePostViewModel.viewModel = ViewModelProvider(this).get(ModulePostViewModel::class.java)
        ModulePostViewModel.viewModel.isClick.observe(
            viewLifecycleOwner, Observer<Boolean> { updateScreen() })
        val binding = DataBindingUtil.inflate<FragmentModulePostBinding>(
            inflater, R.layout.fragment_module_post, container, false
        )
        binding.model = ModulePostViewModel.viewModel
        ModulePostViewModel.viewModel.id = args.moduleId.toLong()
        binding.lifecycleOwner = this
        GlobalScope.launch {
            try {
                val module: ModulePost? =
                    WebAccess.pediatryApi.getModuleById(args.moduleId.toLong()).body()?.response
                ModulePostViewModel.listSlides = module!!.slides
                for (x in module.slides.indices)
                    listSlidesFragment.add(SlidePagerFragment(x))
                withContext(Dispatchers.Main) {
                    viewPager.adapter = SlidePagerAdapter(parentFragmentManager, listSlidesFragment)
                    viewPager.setPageTransformer(true, ZoomPager())
                    viewPager.offscreenPageLimit = 2
                    updateScreen()
                    progressBarView.visibility = View.GONE
                    border.visibility = View.VISIBLE
                    if (module.testStatus == 2 || module.testStatus == 3) {
                        isVisibleTest = false
                        toTesting.visibility = View.GONE
                    }
                    binding.moduleNum.text = getString(R.string.module_is, module.number)
                    binding.tvTitle.text = module.title
                    binding.nextView.setOnClickListener {
                        viewPager.currentItem += 1
                        updateScreen()
                    }
                    binding.backView.setOnClickListener {
                        viewPager.currentItem -= 1
                        updateScreen()
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

    private fun updateScreen() {
        if (ModulePostViewModel.isTooltips) {
            viewPager.alpha = (1).toFloat()
            ModulePostViewModel.isTooltips = false
            moduleNum.visibility = View.GONE
            tvTitle.visibility = View.GONE
            toTesting.visibility = View.GONE
            nextView.visibility = View.GONE
            backView.visibility = View.GONE
        } else {
            viewPager.alpha = (0.5).toFloat()
            ModulePostViewModel.isTooltips = true
            moduleNum.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            nextView.visibility = View.VISIBLE
            backView.visibility = View.VISIBLE
            toTesting.visibility = if (isVisibleTest) View.VISIBLE else View.GONE
        }
    }
}