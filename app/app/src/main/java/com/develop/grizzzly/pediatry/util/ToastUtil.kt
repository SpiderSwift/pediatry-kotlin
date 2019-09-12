package com.develop.grizzzly.pediatry.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.android.synthetic.main.custom_toast.view.*

fun showToast(context: Context, layout:Int, message: String ){
    val view = LayoutInflater.from(context).inflate(layout, null)
    view.alpha = 0.6F
    view.tv.text = message
    val toast = Toast(context)
    toast.setGravity(Gravity.TOP,0,40 )
    toast.duration = Toast.LENGTH_LONG
    toast.view = view
    toast.show()
}