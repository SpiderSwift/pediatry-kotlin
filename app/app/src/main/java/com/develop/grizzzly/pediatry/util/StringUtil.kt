package com.develop.grizzzly.pediatry.util

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber


fun String.isEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPhoneNumber(): Boolean {
    return PhoneNumberUtil.getInstance().isPossibleNumber(this,"US")
}

