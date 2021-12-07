package com.ryg.sayhi.aidl; 
 
import com.ryg.sayhi.aidl.Student; 
 
interface IMyService { 
 
  List<Student>  getStudent();
  void addStudent(in Student student); 
} 