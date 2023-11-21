package com.example.keephealth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_kcal")
data class DailyKcal(
    @PrimaryKey
    var date: String,
    @ColumnInfo(name = "start_time_milli")
    val kcalInfo: String
)