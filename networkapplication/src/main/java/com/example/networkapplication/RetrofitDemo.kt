package com.example.networkapplication

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//定以接口
internal interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<Owner>>

    @GET("users/{user}/repos")
    fun listReposRxJava(@Path("user") user: String?): Observable<List<Owner>>

    @GET("users/{user}/repos")
    suspend fun listReposCoroutine(@Path("user") user: String?): List<Owner>
}

object RetrofitTest {
    fun testRetrofit() {
        val retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val gitHubService = retrofit.create(GitHubService::class.java)
        //这个call其实是ExecutorCallbackCall
        val call = gitHubService.listRepos("octocat")

        call.enqueue(object : Callback<List<Owner>> {
            override fun onResponse(call: Call<List<Owner>>, response: Response<List<Owner>>) {
                val owners = response.body()!!
                Log.i("RetrofitTest", owners[0].id.toString() + "")
            }

            override fun onFailure(call: Call<List<Owner>>, t: Throwable) {
                Log.i("RetrofitTest", "error")
            }
        })
    }

    fun testWithRxJava() {
        val retrofit = Retrofit.Builder()
            .client(
                OkHttpClient
                    .Builder()
                    .build()
            )
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val gitHubService = retrofit.create(GitHubService::class.java)
        val observable = gitHubService.listReposRxJava("octocat")
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Owner>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(owners: List<Owner>) {
                    Log.i("RetrofitTest", owners[0].id.toString() + "")
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

    fun testWithCoroutine() {
        val retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        val gitHubService = retrofit.create(GitHubService::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            val owners = gitHubService.listReposCoroutine("octocat")
            withContext(Dispatchers.Main) {
                Log.i("RetrofitTest", owners[0].id.toString() + "")
            }
        }

    }
}