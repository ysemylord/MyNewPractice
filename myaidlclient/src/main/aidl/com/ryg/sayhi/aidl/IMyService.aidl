package com.ryg.sayhi.aidl; 
 
import com.ryg.sayhi.aidl.Student; 
 
interface IMyService { 
  String getStudentId(String name);
  List<Student>  getStudent();
  void addStudent(in Student student);
  oneway void addStudentName(String name);
} 