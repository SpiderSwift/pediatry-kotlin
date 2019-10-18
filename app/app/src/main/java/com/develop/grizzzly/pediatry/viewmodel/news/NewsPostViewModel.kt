package com.develop.grizzzly.pediatry.viewmodel.news

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.util.setAuthorizeMessage
import com.develop.grizzzly.pediatry.util.glideLocal
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
        val news = newsViewModel.newsLiveData.value!![index]!!
        if (news.likedByUsers.contains(WebAccess.token().id)) {
            viewModelScope.launch {
                try {
                    val response = WebAccess.pediatryApi.unlikeNews(news.id)
                    if (response.isSuccessful) {
                        liked.value = liked.value?.minus(1)
                        news.liked = news.liked?.minus(1)
                        news.likedByUsers.remove(WebAccess.token().id)
                        newsViewModel.adapter?.notifyItemChanged(index)
                        if (news.likedByUsers.contains(WebAccess.token().id)) {
                            glideLocal(imageView, R.drawable.ic_heart)
                        } else {
                            glideLocal(imageView, R.drawable.ic_unlike)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    val response = WebAccess.pediatryApi.likeNews(news.id)
                    if (response.isSuccessful) {
                        liked.value = liked.value?.plus(1)
                        news.liked = news.liked?.plus(1)
                        news.likedByUsers.add(WebAccess.token().id)
                        newsViewModel.adapter?.notifyItemChanged(index)
                        if (news.likedByUsers.contains(WebAccess.token().id)) {
                            glideLocal(imageView, R.drawable.ic_heart)
                        } else {
                            glideLocal(imageView, R.drawable.ic_unlike)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        @BindingAdapter("rttv:news_post_reference_time")
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