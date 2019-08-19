package com.develop.grizzzly.pediatry.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
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
import android.webkit.HttpAuthHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.develop.grizzzly.pediatry.network.WebAccess
import kotlinx.android.synthetic.main.fragment_news_post.*
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
            val response = WebAccess.pediatryApi.getNewsById(args.newsId.toLong())
            if (response.isSuccessful) {
                val newsPost = response.body()?.response
                withContext(Dispatchers.Main) {
                    //viewModel.text.value = newsPost?.text


                    //tvText.setText(newsPost?.text!!)
                    val htmlString = "<!DOCTYPE html><html><body style = \"text-align:center\"><img src=\"https://www.belightsoft.com/products/imagetricks/img/intro-video-poster@2x.jpg\" alt=\"pageNo\" height=\"100%\" width=\"100%\"></body></html>";
                    tvText.settings.javaScriptEnabled = true

                    tvText.setPadding(16, 0, 16, 0)

                    tvText.webViewClient = object : WebViewClient() {
                        override fun onReceivedHttpAuthRequest(
                            view: WebView?,
                            handler: HttpAuthHandler?,
                            host: String?,
                            realm: String?
                        ) {
                            Log.d("TAG", "RECEIVED")
                            handler!!.proceed("m5edu_dev", "_p3Y3QPGuG")
                        }
                    }
                    tvText.loadDataWithBaseURL("https://dev.edu-pediatrics.com/", newsPost?.text, "text/html", "UTF-8", "about:blank")

                    viewModel.title.value = newsPost?.title
                }

            }
        }

        super.onViewCreated(view, savedInstanceState)
    }


    //    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        activity?.menuInflater?.inflate(R.menu.action_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
}