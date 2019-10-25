package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.HttpAuthHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.databinding.FragmentNewsPostBinding
import com.develop.grizzzly.pediatry.images.glideLocal
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.viewmodel.news.NewsPostViewModel
import com.develop.grizzzly.pediatry.viewmodel.news.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news_post.*
import kotlinx.coroutines.*

class NewsPostFragment : Fragment() {

    private lateinit var viewModel: NewsPostViewModel

    private val args: NewsPostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as? MainActivity
        activity?.supportActionBar?.show()
        activity?.toolbarTitle?.text = "Новость"
        activity?.bottom_nav?.visibility = View.VISIBLE
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(NewsPostViewModel::class.java)
        viewModel.time = args.date
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        GlobalScope.launch {
            try {
                val response = WebAccess.pediatryApi.getNewsById(args.newsId.toLong())
                if (response.isSuccessful) {
                    val newsPost = response.body()?.response
                    withContext(Dispatchers.Main) {
                        tvText.settings.javaScriptEnabled = true
                        val model = activity?.run {
                            ViewModelProvider(this).get(NewsViewModel::class.java)
                        } ?: throw Exception("activity is null")
                        val news = model.newsLiveData.value!![args.index]!!
                        viewModel.liked.value = news.liked
                        viewModel.liked.observe(this@NewsPostFragment, Observer {
                            tvLike.text = it.toString()
                        })
                        viewModel.imageView = ivLike
                        viewModel.newsViewModel = model
                        viewModel.index = args.index
                        glideLocal(
                            ivLike,
                            if (news.likedByUsers.contains(WebAccess.token().id)) R.drawable.ic_heart
                            else R.drawable.ic_unlike
                        )
                        tvText.webViewClient = object : WebViewClient() {
                            override fun onReceivedHttpAuthRequest(
                                view: WebView?,
                                handler: HttpAuthHandler?,
                                host: String?,
                                realm: String?
                            ) {
                                handler!!.proceed("m5edu_dev", "_p3Y3QPGuG")
                            }
                        }
                        tvText.loadDataWithBaseURL(
                            "https://dev.edu-pediatrics.com/",
                            newsPost?.text,
                            "text/html",
                            "UTF-8",
                            "about:blank"
                        )
                        viewModel.title.value = newsPost?.title
                        viewModel.announcePicture.value = newsPost?.picture
                        delay(200)
                        mainContent?.visibility = View.VISIBLE
                        load?.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    delay(200)
                    load?.visibility = View.GONE
                    errorMsg?.visibility = View.VISIBLE
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

}