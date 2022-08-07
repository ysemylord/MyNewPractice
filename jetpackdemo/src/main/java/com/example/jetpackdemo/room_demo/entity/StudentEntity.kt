package com.example.jetpackdemo.room_demo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "password") var password: String? = null,
    @ColumnInfo(name = "flag") var flag: Int = 0
)

/**
 * 用于查询时，只接受两个字段
 */
data class StudentTuple(
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "password") var password: String? = null
)
