package com.develop.grizzzly.pediatry.viewmodel.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.adapters.news.NewsAdapter
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.fragments.NewsFragmentDirections
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.News
import kotlinx.coroutines.launch

class NewsItemViewModel constructor(val news: News, val adapter: NewsAdapter, val item: Int) :
    ViewModel() {

    fun onNews(view: View) {
        val toNewsPost = NewsFragmentDirections.actionNewsToNewsPost()
        toNewsPost.newsId = news.id.toInt()
        toNewsPost.date = news.date!!.time
        toNewsPost.index = item
        Navigation.findNavController(view)
            .navigateNoExcept(toNewsPost)
    }

    fun onAd(view: View) {
        var adsUriStr = news.attachedUrl ?: return
        if (!adsUriStr.startsWith("http://") &&
                !adsUriStr.startsWith("https://")) {
            adsUriStr = "http://${adsUriStr}"
        }
        try {
            val adsUri = Uri.parse(adsUriStr)
            startActivity(view.context, Intent(Intent.ACTION_VIEW, adsUri), Bundle())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onLike(@Suppress("UNUSED_PARAMETER") v: View) {
        viewModelScope.launch {
            val isLiked = news.likedByUsers.contains(WebAccess.token().id)
            try {
                val response =
                    if (isLiked) WebAccess.pediatryApi.unlikeNews(news.id)
                    else WebAccess.pediatryApi.likeNews(news.id)
                if (response.isSuccessful) {
                    if (isLiked) {
                        news.liked = news.liked?.minus(1)
                        news.likedByUsers.remove(WebAccess.token().id)
                    } else {
                        news.liked = news.liked?.plus(1)
                        news.likedByUsers.add(WebAccess.token().id)
                    }
                    adapter.notifyItemChanged(item)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}