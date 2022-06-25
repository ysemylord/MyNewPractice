package com.example.myfragmentdemo

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders


/**
 * 先调用Activity.onCreate
 * 再恢复Activity
 */

class MyViewModel : ViewModel() {
    var NumData: String = "原来MyViewModel中的变量"
    override fun onCleared() {
        super.onCleared()
        Log.i("MyViewModel", "调用了销毁方法")
    }
}

/**
 * Activity销毁的时候，
 * 1.普通的成员是不保存的
 * 2.ViewModel中的数据是保存在NonConfigurationInstances中，在旋转屏幕等配置原因造成Activity销毁重建时，能保存和恢复
 * 3.onSaveInstanceState 中保存的数据在应用杀死后恢复Activity也能恢复
 */
class MainActivity : AppCompatActivity() {
    var activityFiled = "原来的Activity的成员变量"
    var activityFiled2 = "原来的Activity要存在state中的成员变量"
    val myViewModel: MyViewModel by viewModels<MyViewModel>()
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("savedString",activityFiled2)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        supportFragmentManager.fragmentFactory = MyFragmentFactory {
            Toast.makeText(
                this@MainActivity,
                "msg",
                Toast.LENGTH_LONG
            ).show()
        }
        super.onCreate(savedInstanceState)
        savedInstanceState?.run {
            activityFiled2 = getString("savedString","默认值")
        }
        findViewById<TextView>(R.id.tv).text = "${myViewModel.NumData}\n $activityFiled \n $activityFiled2"


    }

    fun showFragment(view: View) {
        myViewModel.NumData="改了的MyViewModel中的变量"
        activityFiled ="改了的Activity的成员变量"
        activityFiled2 ="改了的Activity要存在state中的成员变量"
        findViewById<TextView>(R.id.tv).text = "${myViewModel.NumData}\n $activityFiled \n $activityFiled2"
        supportFragmentManager.beginTransaction()
            .add(R.id.container, BlankFragment::class.java, null)
            .commit()
    }
}

class MyFragmentFactory(private val onclick: () -> Unit) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val clazz = loadFragmentClass(classLoader, className)
        if (clazz == BlankFragment::class.java) {
            return BlankFragment(onclick)
        }
        return super.instantiate(classLoader, className)
    }
}