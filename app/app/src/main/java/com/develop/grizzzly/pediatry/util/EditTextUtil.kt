package com.develop.grizzzly.pediatry.util

import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener


fun EditText.addMask(mask: String){
    val listener = MaskedTextChangedListener(mask, this)
    this.addTextChangedListener(listener)
}
