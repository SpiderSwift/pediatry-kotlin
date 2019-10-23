package com.develop.grizzzly.pediatry.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.HttpAuthHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.activities.MainActivity
import com.develop.grizzzly.pediatry.databinding.FragmentNewsPostBinding
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.images.glideLocal
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
        val mainActivity = activity as? MainActivity
        mainActivity?.supportActionBar?.show()
        mainActivity?.toolbarTitle?.text = "Новость"
        mainActivity?.bottom_nav?.visibility = View.VISIBLE
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(NewsPostViewModel::class.java)
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
                            ViewModelProviders.of(this).get(NewsViewModel::class.java)
                        }!!
                        viewModel.liked.value = model.newsLiveData.value!![args.index]!!.liked
                        viewModel.liked.observe(this@NewsPostFragment, Observer {
                            tvLike.text = it.toString()
                        })
                        viewModel.imageView = ivLike
                        viewModel.newsViewModel = model
                        viewModel.index = args.index
                        if (model.newsLiveData.value!![args.index]!!.likedByUsers.contains(WebAccess.token().id))
                            glideLocal(ivLike, R.drawable.ic_heart)
                        else
                            glideLocal(ivLike, R.drawable.ic_unlike)
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
                delay(200)
                withContext(Dispatchers.Main) {
                    try {
                        load.visibility = View.GONE
                        errorMsg.visibility = View.VISIBLE
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

}