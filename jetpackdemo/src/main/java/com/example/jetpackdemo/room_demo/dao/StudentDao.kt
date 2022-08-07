package com.example.jetpackdemo.room_demo.dao

import androidx.room.*
import com.example.jetpackdemo.room_demo.entity.StudentEntity
import com.example.jetpackdemo.room_demo.entity.StudentTuple

@Dao
interface StudentDao {
    @Insert
    fun insertStudent(vararg studentEntity: StudentEntity)

    @Delete
    fun deleteStudent(studentEntity: StudentEntity)

    @Update
    fun updateStudent(studentEntity: StudentEntity)

    @Query("SELECT * FROM student_table")
    fun queryAll(): List<StudentEntity>

    @Query("SELECT * FROM student_table where name like :name")
    fun queryByName(name: String): StudentEntity

    @Query("SELECT name,password FROM student_table")
    fun queryAll2(): List<StudentTuple>
}
