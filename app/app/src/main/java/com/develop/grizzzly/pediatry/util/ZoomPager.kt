package com.develop.grizzzly.pediatry.util

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class ZoomPager : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> alpha = 0f
                position <= 1 -> {
                    val scaleFactor = 0.85f.coerceAtLeast(1 - abs(position))
                    val verticalMargin = pageHeight * (1 - scaleFactor) / 2
                    val horizontalMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) horizontalMargin - verticalMargin / 2
                    else horizontalMargin + verticalMargin / 2
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                    alpha = (0.5f + (((scaleFactor - 0.85f) / (1 - 0.85f)) * (1 - 0.5f)))
                }
                else -> alpha = 0f
            }
        }
    }
}
