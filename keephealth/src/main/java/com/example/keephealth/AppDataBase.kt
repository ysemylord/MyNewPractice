package com.example.keephealth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DailyKcal::class], version = 3, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract val dailyKcalDao: DailyKcalDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, AppDataBase::class.java, "app_database"
                    ).build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }


}
