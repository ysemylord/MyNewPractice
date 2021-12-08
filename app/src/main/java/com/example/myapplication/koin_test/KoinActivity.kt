package com.example.myapplication.koin_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel as viewModel1

class KoinActivity : AppCompatActivity() {

    val scope = getKoin().getOrCreateScope("myScope", named("MY_SCOPE"))



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_koin)
        val person1 by inject<Person>()
        val person2 by inject<Person>()
        val person3 by inject<Person>()
        val person: Person = get()
        val animation:Animation = get()
        person1.speak()
        person2.speak()
        person3.speak()
        Log.i("koin person", person1.hashCode().toString())
        Log.i("koin person", person2.hashCode().toString())
        Log.i("koin person", person3.hashCode().toString())

        val userData1 by inject<UserData>()
        val userData2 by inject<UserData>()
        Log.i("koin userData1", userData1.hashCode().toString())
        Log.i("koin userData2", userData2.hashCode().toString())
        val myViewModel: MyViewModel by viewModel()

        val bean1: AliveOnlyInActivityBean = scope.get()
        val bean2: AliveOnlyInActivityBean = scope.get()
        Log.i("koin scope", (bean1 === bean2).toString())

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.closed
    }
}