package com.example.maidlclient

import android.content.Context
import android.os.BatteryManager
import android.util.Log

fun testBatteryService(context: Context) {
    val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    batteryManager.run {
        val capacity = getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        val average = getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE)
        Log.i("batteryManager", "capacity:$capacity,average: $average")
    }
}