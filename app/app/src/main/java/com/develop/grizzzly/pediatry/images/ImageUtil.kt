package com.develop.grizzzly.pediatry.images

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.develop.grizzzly.pediatry.application.ThisApp
import java.io.InputStream
import kotlin.math.floor
import kotlin.math.sqrt

private const val TAG = "ImageUtil"

fun glideLocal(imageView: ImageView, placeholderId: Int?) {
    placeholderId?.let {
        Glide.with(ThisApp.app)
            .load(it)
            .centerCrop()
            .into(imageView)
    }
}

fun glideRemote(path: String?, imageView: ImageView, placeholderId: Int) {
    if (!path.isNullOrBlank()) {
        Log.d(TAG, "glideRemote path: $path")
        Glide.with(ThisApp.app)
            .load(path)
            .centerCrop()
            .placeholder(placeholderId)
            .error(placeholderId)
            .into(imageView)
    }
}

fun picassoRemoteWithAuth(path: String?, imageView: ImageView, placeholderId: Int) {
    if (!path.isNullOrBlank()) {
        Log.d(TAG, "picassoRemoteWithAuth path: $path")
        ImageAccess.picasso.load(path)
            .fit()
            .centerCrop()
            .placeholder(placeholderId)
            .error(placeholderId)
            .into(imageView)
    }
}

fun minimizeImage(uri: Uri, contentResolver: ContentResolver): Uri? {
    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
    val maxBytes = 1024L * 1024
    val inputStream = contentResolver.openInputStream(uri)
    val rotatedBitmap = inputStream?.let {
        rotateImageIfRequired(it, bitmap)
    }
    val scaledBitmap = rotatedBitmap?.let {
        scaleBitmap(it, maxBytes)
    }
    val path = MediaStore.Images.Media.insertImage(
        contentResolver, scaledBitmap, "Profile image", null
    )
    return Uri.parse(path)
}

fun scaleBitmap(bitmap: Bitmap, maxBytes: Long): Bitmap {
    val currentWidth = bitmap.width
    val currentHeight = bitmap.height
    val currentPixels = currentWidth * currentHeight
    val maxPixels = maxBytes / 4
    if (currentPixels <= maxPixels)
        return bitmap
    val scaleFactor = sqrt(maxPixels / currentPixels.toDouble())
    val newWidthPx = floor(currentWidth * scaleFactor).toInt()
    val newHeightPx = floor(currentHeight * scaleFactor).toInt()
    return Bitmap.createScaledBitmap(
        bitmap, newWidthPx, newHeightPx, true
    )
}

fun rotateImageIfRequired(inputStream: InputStream, bitmap: Bitmap): Bitmap {
    val rotatedBitmap: Bitmap
    val exif = ExifInterface(inputStream)
    val orientation = exif.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_UNDEFINED)
    rotatedBitmap = when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
        else -> bitmap
    }
    return rotatedBitmap
}