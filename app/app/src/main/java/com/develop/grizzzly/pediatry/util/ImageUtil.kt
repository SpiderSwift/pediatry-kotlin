package com.develop.grizzzly.pediatry.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.develop.grizzzly.pediatry.MainApplication
import java.io.ByteArrayOutputStream
import kotlin.math.floor
import kotlin.math.sqrt


fun setImageGlide(path: String, imageView: ImageView, placeholderId: Int) {
    Glide.with(MainApplication.get()!!)
        .load(path)
        .centerCrop()
        .placeholder(placeholderId)
        .error(placeholderId)
        .into(imageView)
}

fun setAuthorizeMessage(path: String, imageView: ImageView, placeholderId: Int) {

    val picasso = MainApplication.get()!!.picasso
    picasso.load(path)
        .fit()
        .centerCrop()
        .placeholder(placeholderId)
        .error(placeholderId)
        .into(imageView)
}

fun minimizeImage(uri: Uri, contentResolver: ContentResolver): Uri? {
    val matrix = Matrix()
    matrix.postRotate(90F)
    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
    val maxBytes = 1024L * 1024

    val scaledBitmap = scaleBitmap(bitmap, maxBytes)
    val rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0,0, scaledBitmap.width, scaledBitmap.height, matrix, true)

    val path =
        MediaStore.Images.Media.insertImage(contentResolver, rotatedBitmap, "Profile image", null)
    return Uri.parse(path)
}

fun scaleBitmap(input: Bitmap, maxBytes: Long): Bitmap {
    val currentWidth = input.width
    val currentHeight = input.height
    val currentPixels = currentWidth * currentHeight

    val maxPixels = maxBytes / 4
    if (currentPixels <= maxPixels) {
        return input
    }

    val scaleFactor = sqrt(maxPixels / currentPixels.toDouble())
    val newWidthPx = floor(currentWidth * scaleFactor).toInt()
    val newHeightPx = floor(currentHeight * scaleFactor).toInt()
    return Bitmap.createScaledBitmap(input, newWidthPx, newHeightPx, true)
}