package com.develop.grizzzly.pediatry.viewmodel.news

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.images.glideLocal
import com.develop.grizzzly.pediatry.images.setAuthorizeMessage
import com.develop.grizzzly.pediatry.network.WebAccess
import com.github.curioustechizen.ago.RelativeTimeTextView
import kotlinx.coroutines.launch

class NewsPostViewModel : ViewModel() {

    val title = MutableLiveData<String>().apply { value = "" }
    val text = MutableLiveData<String>().apply { value = "" }
    var time: Long = 0
    val announcePicture = MutableLiveData<String>().apply { value = "" }
    lateinit var newsViewModel: NewsViewModel
    lateinit var imageView: ImageView
    var index: Int = 0
    var liked = MutableLiveData<Long>()

    fun onLike(@Suppress("UNUSED_PARAMETER") v : View) {
        viewModelScope.launch {
            try {
                val news = newsViewModel.newsLiveData.value!![index]!!
                val isLiked = news.likedByUsers.contains(WebAccess.token().id)
                val response =
                    if (isLiked) WebAccess.pediatryApi.unlikeNews(news.id)
                    else WebAccess.pediatryApi.likeNews(news.id)
                if (response.isSuccessful) {
                    if (isLiked) {
                        liked.value = liked.value?.dec()
                        news.liked = news.liked?.dec()
                        news.likedByUsers.remove(WebAccess.token().id)
                    } else {
                        liked.value = liked.value?.inc()
                        news.liked = news.liked?.inc()
                        news.likedByUsers.add(WebAccess.token().id)
                    }
                    newsViewModel.adapter?.notifyItemChanged(index)
                    glideLocal(
                        imageView,
                        if (isLiked) R.drawable.ic_unlike
                        else R.drawable.ic_heart
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        @BindingAdapter("news_post_reference_time")
        @JvmStatic
        fun setReferenceTime(view: RelativeTimeTextView, time: Long) {
            view.setReferenceTime(time)
        }

        @BindingAdapter("newsImageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            setAuthorizeMessage(imageUrl, view, android.R.color.white)
        }
    }

}