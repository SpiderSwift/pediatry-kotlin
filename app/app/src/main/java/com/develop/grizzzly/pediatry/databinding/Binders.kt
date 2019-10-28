package com.develop.grizzzly.pediatry.databinding

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.develop.grizzzly.pediatry.images.glideLocal
import com.develop.grizzzly.pediatry.images.glideRemote
import com.develop.grizzzly.pediatry.images.picassoRemoteWithAuth
import com.github.curioustechizen.ago.RelativeTimeTextView

@BindingMethods(
    value = [
        BindingMethod(
            type = RelativeTimeTextView::class,
            attribute = "reference_time",
            method = "setReferenceTime"
        ),
        BindingMethod(
            type = ImageView::class,
            attribute = "zvaLoadImage",
            method = "loadImage"
        ),
        BindingMethod(
            type = ImageView::class,
            attribute = "zvaLoadImageWithAuth",
            method = "loadImageWithAuth"
        ),
        BindingMethod(
            type = ImageView::class,
            attribute = "zvaLoadImageFromRes",
            method = "loadImage"
        )
    ]
)
object Binders {
    @BindingAdapter("reference_time")
    @JvmStatic
    fun setReferenceTime(view: RelativeTimeTextView, time: Long) {
        Log.w("Binders", "RelativeTimeTextView setReferenceTime")
        view.setReferenceTime(time)
    }

    @BindingAdapter("zvaLoadImage")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: Uri?) {
        Log.w("Binders", "CircleImageView loadImage uri")
        imageUrl?.let {
            glideRemote(it.toString(), view, android.R.color.white)
        }
    }

    @BindingAdapter("zvaLoadImageWithAuth")
    @JvmStatic
    fun loadImageWithAuth(view: ImageView, imageUrl: String?) {
        Log.w("Binders", "ImageView loadImageWithAuth url string")
        picassoRemoteWithAuth(imageUrl, view, android.R.color.white)
    }

    @BindingAdapter("zvaLoadImageFromRes")
    @JvmStatic
    fun loadImage(view: ImageView, resource: Int?) {
        Log.w("Binders", "ImageView loadImage resource")
        glideLocal(view, resource)
    }
}