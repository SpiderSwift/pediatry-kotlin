package com.develop.grizzzly.pediatry.viewmodel.news

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.util.setAuthorizeMessage
import com.github.curioustechizen.ago.RelativeTimeTextView

class NewsPostViewModel : ViewModel() {

    val title = MutableLiveData<String>().apply { value = "" }
    val text = MutableLiveData<String>().apply { value = "" }
    var time : Long = 0
    val announcePicture = MutableLiveData<String>().apply { value = "" }


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