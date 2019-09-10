package com.develop.grizzzly.pediatry.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.develop.grizzzly.pediatry.MainApplication
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
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
    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
    val maxBytes = 1024L * 1024

    val inputStream = contentResolver.openInputStream(uri)

    val rotatedBitmap = rotateImageIfRequired(inputStream, bitmap)
    val scaledBitmap = scaleBitmap(rotatedBitmap, maxBytes)

    val path =
        MediaStore.Images.Media.insertImage(contentResolver, scaledBitmap, "Profile image", null)

    return Uri.parse(path)
}

fun scaleBitmap(bitmap: Bitmap, maxBytes: Long): Bitmap {
    val currentWidth = bitmap.width
    val currentHeight = bitmap.height
    val currentPixels = currentWidth * currentHeight

    val maxPixels = maxBytes / 4
    if (currentPixels <= maxPixels) {
        return bitmap
    }

    val scaleFactor = sqrt(maxPixels / currentPixels.toDouble())
    val newWidthPx = floor(currentWidth * scaleFactor).toInt()
    val newHeightPx = floor(currentHeight * scaleFactor).toInt()

    return Bitmap.createScaledBitmap(bitmap, newWidthPx, newHeightPx, true)
}

fun rotateImageIfRequired(inputStream: InputStream, bitmap: Bitmap): Bitmap{
    val rotatedBitmap: Bitmap
    val exif = ExifInterface(inputStream)
    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

    rotatedBitmap = when(orientation){
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
        else -> bitmap
    }

    return rotatedBitmap
}