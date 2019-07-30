package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.develop.grizzzly.pediatry.databinding.FragmentNewsPostBinding
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.viewmodel.news.NewsPostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuInflater
import androidx.navigation.fragment.navArgs
import com.develop.grizzzly.pediatry.network.WebAccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext




class NewsPostFragment : Fragment() {


    private lateinit var viewModel: NewsPostViewModel

    private val args: NewsPostFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mainActivity = activity as? MainActivity
        mainActivity?.supportActionBar?.show()
        mainActivity?.bottom_nav?.visibility = View.VISIBLE
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(NewsPostViewModel::class.java)

        GlobalScope.launch {
            val response = WebAccess.pediatryApi.getNewsById(args.newsId.toLong())
            if (response.isSuccessful) {
                val newsPost = response.body()?.response
                withContext(Dispatchers.Main) {
                    viewModel.text.value = newsPost?.text
                    viewModel.title.value = newsPost?.title
                }

            }
        }



        val binding = DataBindingUtil.inflate<FragmentNewsPostBinding>(
            inflater,
            R.layout.fragment_news_post,
            container,
            false
        )

        binding.model = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }


//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        activity?.menuInflater?.inflate(R.menu.action_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
}