package com.develop.grizzzly.pediatry.viewmodel.news

import android.util.Log
import android.view.View
import com.github.curioustechizen.ago.RelativeTimeTextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.network.model.News
import java.util.*


class NewsItemViewModel constructor(val news : News) : ViewModel() {

    fun onNews(view : View) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_news_to_news_post)
    }

    fun onLike(view : View) {

    }

    companion object {
        @BindingAdapter("rttv:reference_time")
        @JvmStatic
        fun setReferenceTime(view: RelativeTimeTextView, time: Long) {
            view.setReferenceTime(time)
        }
    }
}