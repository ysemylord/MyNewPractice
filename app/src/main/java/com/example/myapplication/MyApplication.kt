package com.example.myapplication

import android.app.Application
import com.example.myapplication.koin_test.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

class MyApplication : Application() {
    private val appModule = module {
        factory {
            //每次会创建新的
            Person()
        } bind Animation::class
        single { //单例
            UserData()
        }

        single {
            //通过get()自动注入UserData
            ComUserData(get())
        }

        viewModel {
            MyViewModel()
        }

        scope(named("MY_SCOPE")) {
            scoped {
               AliveOnlyInActivityBean("hello")
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }


}