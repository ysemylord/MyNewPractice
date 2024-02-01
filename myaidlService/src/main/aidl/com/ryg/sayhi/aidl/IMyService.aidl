package com.ryg.sayhi.aidl; 
 
import com.ryg.sayhi.aidl.Student; 
import com.ryg.sayhi.aidl.ICallBack;

interface IMyService { 
  String getStudentId(String name);
  List<Student>  getStudent();
  void addStudent(in Student student);
<<<<<<< HEAD
  oneway void addStudentName(String name);
=======
  String getConvertName_In(in Student student);
  void getStudentInfo_Out(out Student student);
  void getStudengInfo_Inout(inout Student studeng);
  void setCallback(ICallBack iCallBack);
>>>>>>> dce5f28f8f10983107c173acb37adac0493eef10
} 