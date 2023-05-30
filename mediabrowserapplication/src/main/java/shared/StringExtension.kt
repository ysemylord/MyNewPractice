package shared

import android.util.Log

fun String.logd(){
    Log.d("com.ttjjttjj.myapplication",this)
}


fun String.loge(){
    Log.e("com.ttjjttjj.myapplication",this)
}