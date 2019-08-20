package com.develop.grizzzly.pediatry.viewmodel.news

import android.util.Log
import android.view.View
import android.widget.ImageView
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
import com.develop.grizzzly.pediatry.util.setAuthorizeMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NewsItemViewModel constructor(val news : News, val adapter : NewsAdapter, val item : Int) : ViewModel() {

    fun onNews(view : View) {
        val navController = Navigation.findNavController(view)
        val toNewsPost = NewsFragmentDirections.actionNewsToNewsPost()
        toNewsPost.newsId = news.id.toInt()
        toNewsPost.date = news.date!!.time
        toNewsPost.index = item
        navController.navigate(toNewsPost)
    }

    fun onLike(view : View) {
        if (news.likedByUsers.contains(WebAccess.id)) {
            viewModelScope.launch {
                val response = WebAccess.pediatryApi.unlikeNews(news.id)
                if (response.isSuccessful) {
                    news.liked = news.liked?.minus(1)
                    news.likedByUsers.remove(WebAccess.id)
                    adapter.notifyItemChanged(item)
                }
            }
        } else {
            viewModelScope.launch {
                val response = WebAccess.pediatryApi.likeNews(news.id)
                if (response.isSuccessful) {
                    news.liked = news.liked?.plus(1)
                    news.likedByUsers.add(WebAccess.id)
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

        @BindingAdapter("bind:newsImageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (imageUrl?.isNotEmpty() == true) {
                Log.d("TAG", imageUrl)
                setAuthorizeMessage(imageUrl, view, android.R.color.white)
            }

        }
    }
}