package com.develop.grizzzly.pediatry.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
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
import com.develop.grizzzly.pediatry.network.model.Module
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

    override fun onCreateView( //todo изменить картинку load and create books
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        (activity as? MainActivity)?.supportActionBar?.hide()
        activity?.toolbarTitle?.text = "Модуль"
        activity?.bottom_nav?.visibility = View.GONE
        val binding = DataBindingUtil.inflate<FragmentModulePostBinding>(
            inflater, R.layout.fragment_module_post, container, false
        )
        ModulePostViewModel.viewModel = ViewModelProvider(this).get(ModulePostViewModel::class.java)
        ModulePostViewModel.viewModel.id = args.moduleId.toLong()
        binding.model = ModulePostViewModel.viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        GlobalScope.launch {
            val listSlidesFragment = mutableListOf<SlidePagerFragment>()
            try {
                val module: ModulePost =
                    WebAccess.pediatryApi.getModuleById(ModulePostViewModel.viewModel.id).body()!!.response!!
                ModulePostViewModel.listSlides = module.slides
                for (x in module.slides.indices)
                    listSlidesFragment.add(SlidePagerFragment(x))
                withContext(Dispatchers.Main) {
                    ModulePostViewModel.viewModel.isClicked.observe(
                        viewLifecycleOwner, Observer<Boolean> { updateScreen(module.testStatus) })
                    viewPager.adapter = SlidePagerAdapter(childFragmentManager, listSlidesFragment)
                    viewPager.setPageTransformer(true, ZoomPager())
                    viewPager.offscreenPageLimit = 2
                    updateScreen(module.testStatus)
                    progressBarView.visibility = View.GONE
                    border.visibility = View.VISIBLE
                    if (module.testStatus != Module.noTesting) toTesting.visibility = View.GONE
                    moduleNum.text = getString(R.string.module_is, module.number)
                    tvTitle.text = module.title
                    nextView.setOnClickListener {
                        viewPager.currentItem += 1
                        updateScreen(module.testStatus)
                    }
                    backView.setOnClickListener {
                        viewPager.currentItem -= 1
                        updateScreen(module.testStatus)
                    }
                }
            } catch (e: Exception) {
                Log.println(Log.ASSERT, "Ошибка", e.toString())
                withContext(Dispatchers.Main) {
                    progressBarView.visibility = View.GONE
                    includeError.visibility = View.VISIBLE
                    errorMsg.text = "Ошибка :("
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateScreen(moduleTestStatus: Int) {
        if (ModulePostViewModel.isTooltips) {
            viewPager.alpha = (1).toFloat()
            ModulePostViewModel.isTooltips = false
            moduleNum.visibility = View.GONE
            tvTitle.visibility = View.GONE
            toTesting.visibility = View.GONE
            nextView.visibility = View.GONE
            backView.visibility = View.GONE
        } else {
            viewPager.alpha = (0.1).toFloat()
            ModulePostViewModel.isTooltips = true
            moduleNum.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            nextView.visibility = View.VISIBLE
            backView.visibility = View.VISIBLE
            toTesting.visibility =
                if (moduleTestStatus == Module.noTesting) View.VISIBLE else View.GONE
        }
    }
}