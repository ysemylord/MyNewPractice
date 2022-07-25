package com.example.jetpackdemo.base_use

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JetPackBaseUseViewModel : ViewModel() {
    val number = MutableLiveData<Int>()
    fun numberPlus() {
        val newValue = number.value?.plus(1)
        number.value = newValue
    }

    init {
        number.value = 0
    }
}