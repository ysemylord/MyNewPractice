package com.gson_demo

import com.google.gson.Gson

data class Noti(
    val caption_a: String="You're a Top Fan of janredone!",
    val caption_b: String="3",
    val creator_guid: String="4",
    val fanname: String="5",
    val hostname: String="5",
    val notif_id: String="",
    val timestamp: Long=System.currentTimeMillis(),
    val top_fan_img_url: String="r",
    val type: Int=312,
    val viewer_guid: String="28XJEQTWPWhs4S5d"
)
class 模拟通知数据 {

}

fun main() {
    val list = mutableListOf<Noti>()
    for(index in 500..510){
        list.add(Noti("$index You're a Top Fan of janredone!", notif_id = "$index"))
    }
    println(Gson().toJson(list))

}