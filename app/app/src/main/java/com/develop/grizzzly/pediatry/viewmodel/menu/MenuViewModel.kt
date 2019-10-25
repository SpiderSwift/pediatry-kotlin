package com.develop.grizzzly.pediatry.viewmodel.menu

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.extensions.navigateNoExcept
import com.develop.grizzzly.pediatry.images.setAuthorizeMessage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {

    val name = MutableLiveData<String>().apply { value = "" }
    val lastname = MutableLiveData<String>().apply { value = "" }
    val avatarUrl = MutableLiveData<String>().apply { value = "" }

    fun onProfile(view: View) {
        viewModelScope.launch {
            delay(100)
            Navigation.findNavController(view)
                .navigateNoExcept(R.id.action_menu_to_profile)
        }

    }

    companion object {
        @BindingAdapter("avatarUrl")
        @JvmStatic
        fun loadImage(view: CircleImageView, imageUrl: String?) {
            setAuthorizeMessage(imageUrl, view, android.R.color.white)
        }
    }

}