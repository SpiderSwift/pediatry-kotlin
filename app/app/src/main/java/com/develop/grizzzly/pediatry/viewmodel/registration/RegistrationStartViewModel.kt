package com.develop.grizzzly.pediatry.viewmodel.registration

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.develop.grizzzly.pediatry.BR
import com.develop.grizzzly.pediatry.util.isPhoneNumber


class RegistrationStartViewModel : BaseObservable() {

    var phoneNumber : String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phoneNumber)
        }
        @Bindable get


    fun isValid(phoneNumber : String) : Boolean {
        return phoneNumber.isPhoneNumber()
    }


}