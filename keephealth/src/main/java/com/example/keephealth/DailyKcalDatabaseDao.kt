package com.example.keephealth

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DailyKcalDatabaseDao {
    @Insert
    fun insert(night: DailyKcal)

    @Update
    fun update(night: DailyKcal)

    @Query("SELECT * from daily_kcal WHERE date = :date")
    fun get(date: String): DailyKcal?

}
