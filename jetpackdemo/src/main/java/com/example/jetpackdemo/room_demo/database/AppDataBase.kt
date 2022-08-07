package com.example.jetpackdemo.room_demo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jetpackdemo.room_demo.SleepNight
import com.example.jetpackdemo.room_demo.dao.SleepDatabaseDao
import com.example.jetpackdemo.room_demo.dao.StudentDao
import com.example.jetpackdemo.room_demo.entity.StudentEntity

@Database(entities = [SleepNight::class, StudentEntity::class], version = 3, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract val sleepDatabaseDao: SleepDatabaseDao

    abstract val studentDao: StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "app_database"
                    ).addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

    /**
     * 数据库增加字段
     */
    object MIGRATION_1_2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("alter table student_table add column flag integer not null default 1")
        }
    }

    /**
     * 删除student_table的addressId的字段
     */
    object MIGRATION_2_3 : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            /**
             * 1.创建临时表
             * 2.拷贝数据到临时表
             * 3.删除原表
             * 4.将临时表的名字改为原表
             */
            database.execSQL("create table student_temp(id integer primary key,name text,password text,flag integer not null default 1)")
            database.execSQL("insert into student_temp(id,name,password,flag) select id,name,password,flag from student_table")
            database.execSQL("drop table student_table")
            database.execSQL("alter table student_temp rename to student_table")
        }
    }

}
