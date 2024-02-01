package com.example.servicemanagerdemo

import android.content.Context
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val batteryManager = getSystemService(Context.BATTERY_SERVICE)  as BatteryManager
        batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        batteryManager.isCharging
    }
}