package com.example.networkapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.networkapplication.test_generic.MyMutableLiveData

class MyViewModel : ViewModel() {
    val name: MutableLiveData<String> = MutableLiveData()
    fun getName() {
        name.value = null

    }
}