package com.example.jetpackdemo.room_demo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpackdemo.room_demo.SleepNight
import com.example.jetpackdemo.room_demo.dao.SleepDatabaseDao

@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDataBase : RoomDatabase() {
    abstract val  sleepDatabaseDao: SleepDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: SleepDataBase? = null

        fun getInstance(context: Context): SleepDataBase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepDataBase::class.java,
                        "sleep_history_database"
                    )

                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}
