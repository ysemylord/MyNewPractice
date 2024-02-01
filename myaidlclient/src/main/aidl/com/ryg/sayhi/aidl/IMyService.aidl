package com.ryg.sayhi.aidl; 
 
import com.ryg.sayhi.aidl.Student; 
import com.ryg.sayhi.aidl.ICallBack;

interface IMyService { 
<<<<<<< HEAD
  String getStudentId(String name);
  List<Student>  getStudent();
  void addStudent(in Student student);
  oneway void addStudentName(String name);
=======
    String getStudentId(String name);
    List<Student>  getStudent();
    void addStudent(in Student student);
    String getConvertName_In(in Student student);
    void getStudentInfo_Out(out Student student);
    void getStudengInfo_Inout(inout Student student);
    void setCallback(ICallBack iCallBack);
>>>>>>> dce5f28f8f10983107c173acb37adac0493eef10
} 