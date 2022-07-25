package com.example.jetpackdemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyViewModel : ViewModel() {
    //这个MyRepository应该放在构造函数里面，用依赖注入的。因为在Mock测试时，可以注入一个mock的对象
    val myRepository = MyRepository()
    val result = MutableLiveData<String>()
    fun getData() {
        viewModelScope.launch {
            val result = myRepository.getData()
            this@MyViewModel.result.value = result
            Log.i("MyViewModel", "$result")
        }
    }
}

class MyRepository() {
    suspend fun getData(): String {
        /*delay(5000)
        val result = "res"
        Log.i("MyRepository","$result")
        return result*/
        return GlobalScope.async {
            delay(5000)
            val result = "res"
            Log.i("MyRepository", "$result")
            result
        }.await()
    }
}

class TestRepositoryScopeFragment : Fragment() {

    val myViewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewModel.result.observe(this, Observer<String> {

        })
        myViewModel.getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_repository_scope_layout, container, false)
    }


}