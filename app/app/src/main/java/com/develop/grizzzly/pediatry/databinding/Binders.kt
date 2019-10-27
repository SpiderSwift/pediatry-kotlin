package com.develop.grizzzly.pediatry.databinding

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.develop.grizzzly.pediatry.images.glideLocal
import com.develop.grizzzly.pediatry.images.glideRemote
import com.github.curioustechizen.ago.RelativeTimeTextView

//@BindingAdapter("imageUri")
//fun loadImage(view: ImageView, imageUri: Uri?, isFit: Boolean = false) {
//    imageUri?.let {
//        loadImage(view, it.toString(), isFit)
//    }
//}
//
//@BindingAdapter("resource")
//fun loadImage(view: ImageView, resource: Int?) {
//    glideLocal(view, resource)
//}
//
@BindingAdapter("reference_time")
fun setReferenceTime(view: RelativeTimeTextView, time: Long) {
    view.setReferenceTime(time)
}
//
//@BindingAdapter("imageUrlStr")
//fun loadImage(view: ImageView, imageUrlStr: String?, isFit: Boolean = false) {
//    glideRemote(imageUrlStr, view, android.R.color.white, isFit)
//}
