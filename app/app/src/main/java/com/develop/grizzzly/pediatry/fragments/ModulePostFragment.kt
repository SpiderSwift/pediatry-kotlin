package com.develop.grizzzly.pediatry.fragments

import android.annotation.SuppressLint
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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModulePostFragment : Fragment() {

    private lateinit var viewModel: ModulePostViewModel

    private val args: ModulePostFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var activeSlide = 0
        val activity = activity as? MainActivity
        val picasso = ImageAccess.picasso
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
            val module = WebAccess.pediatryApi.getModuleById(viewModel.id).body()
                ?.response //todo прихотдят пдф файлы
            withContext(Dispatchers.Main) {
                //todo book and slides
                binding.moduleNum.text = "Модуль ${module!!.number}"
                binding.tvTitle.text = module.title
                picasso.load(module.slides[activeSlide].image).placeholder(R.drawable.loading)
                    .into(binding.moduleImage)
                // loadImages(module, picasso, activeSlide)
                binding.nextView.setOnClickListener {
                    activeSlide++
                    if (activeSlide > module.slides.size - 1) activeSlide = 0
                    picasso.load(module.slides[activeSlide].image).fit()
                        .placeholder(R.drawable.loading).into(binding.moduleImage)
                    // loadImages(module, picasso, activeSlide)
                }
                binding.backView.setOnClickListener {
                    activeSlide--
                    if (activeSlide < 0) activeSlide = module.slides.size - 1
                    picasso.load(module.slides[activeSlide].image).fit()
                        .placeholder(R.drawable.loading).into(binding.moduleImage)
                    //   loadImages(module, picasso, activeSlide)
                }
            }
        }
        return binding.root
    }

    private fun loadImages(modulePost: ModulePost, picasso: Picasso, activeSlide: Int) {
//        var num: Int =
//            activeSlide + if ((activeSlide + 2) > modulePost.slides.size) if ((activeSlide + 1) > modulePost.slides.size) return else 1 else 2
//        while (num != activeSlide) {
//            Log.println(Log.ASSERT, "msg",num.toString())
//            picasso.load(modulePost.slides[num].image)
//            num--
//        }
    }
}