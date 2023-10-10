package com.ryg.sayhi.aidl; 
 
import com.ryg.sayhi.aidl.Student; 
import com.ryg.sayhi.aidl.ICallBack;

interface IMyService { 
  String getStudentId(String name);
  List<Student>  getStudent();
  void addStudent(in Student student);
  String getConvertName_In(in Student student);
  void getStudentInfo_Out(out Student student);
  void getStudengInfo_Inout(inout Student studeng);
  void setCallback(ICallBack iCallBack);
} 