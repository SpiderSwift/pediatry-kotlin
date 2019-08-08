package com.develop.grizzzly.pediatry.viewmodel.menu

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.util.setAuthorizeMessage
import de.hdodenhof.circleimageview.CircleImageView

class MenuViewModel : ViewModel() {

    val name = MutableLiveData<String>().apply { value = "" }
    val lastname = MutableLiveData<String>().apply { value = "" }
    val avatarUrl = MutableLiveData<String>().apply { value = "" }


    companion object {
        @BindingAdapter("bind:avatarUrl")
        @JvmStatic
        fun loadImage(view: CircleImageView, imageUrl: String?) {
            if (imageUrl?.isNotEmpty() == true) {
                setAuthorizeMessage(imageUrl, view, android.R.color.white)
            }

        }
    }


}