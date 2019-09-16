package com.develop.grizzzly.pediatry.util

import android.widget.EditText
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import java.security.NoSuchAlgorithmException


fun String.isEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPhoneNumber(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    return this.length === 18
}


fun String.md5(): String {
    val md5 = "MD5"
    try {
        val digest = java.security.MessageDigest
            .getInstance(md5)
        digest.update(this.toByteArray())
        val messageDigest = digest.digest()

        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2)
                h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()

    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    return ""
}

fun String.formatPhone(): String {
    return this.replace("\\s".toRegex(), "")
}

