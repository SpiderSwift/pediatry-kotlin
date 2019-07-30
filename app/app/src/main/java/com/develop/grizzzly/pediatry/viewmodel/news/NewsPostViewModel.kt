package com.develop.grizzzly.pediatry.viewmodel.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsPostViewModel : ViewModel() {

    val title = MutableLiveData<String>().apply { value = "" }
    val text = MutableLiveData<String>().apply { value = "" }

}