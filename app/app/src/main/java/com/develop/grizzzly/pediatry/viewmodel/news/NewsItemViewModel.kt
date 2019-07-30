package com.develop.grizzzly.pediatry.viewmodel.news

import android.view.View
import com.github.curioustechizen.ago.RelativeTimeTextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.adapters.news.NewsAdapter
import com.develop.grizzzly.pediatry.fragments.NewsFragmentDirections
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.News
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NewsItemViewModel constructor(val news : MutableLiveData<News>, val adapter : NewsAdapter, val item : Int) : ViewModel() {

    fun onNews(view : View) {
        val navController = Navigation.findNavController(view)
        val toNewsPost = NewsFragmentDirections.actionNewsToNewsPost()
        toNewsPost.newsId = news.value?.id?.toInt() ?: 0
        navController.navigate(toNewsPost)
    }

    fun onLike(view : View) {
        if (news.value?.likedByUsers?.contains(WebAccess.id) == true) {
            viewModelScope.launch {
                val response = WebAccess.pediatryApi.unlikeNews(news.value?.id)
                if (response.isSuccessful) {
                    news.value?.liked = news.value?.liked?.minus(1)
                    news.value?.likedByUsers?.remove(WebAccess.id)
                    adapter.notifyItemChanged(item)
                }
            }
        } else {
            viewModelScope.launch {
                val response = WebAccess.pediatryApi.likeNews(news.value?.id)
                if (response.isSuccessful) {
                    news.value?.liked = news.value?.liked?.plus(1)
                    news.value?.likedByUsers?.add(WebAccess.id)
                    adapter.notifyItemChanged(item)
                }
            }
        }

    }

    companion object {
        @BindingAdapter("rttv:reference_time")
        @JvmStatic
        fun setReferenceTime(view: RelativeTimeTextView, time: Long) {
            view.setReferenceTime(time)
        }
    }
}