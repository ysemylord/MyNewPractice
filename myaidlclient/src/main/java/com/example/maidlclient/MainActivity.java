package com.example.maidlclient;


import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.ryg.sayhi.aidl.IMyService;
import com.ryg.sayhi.aidl.Student;

public class MainActivity extends Activity {
    private static final String ACTION_BIND_SERVICE = "com.ryg.sayhi.MyService";
    private IMyService mIMyService;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        private static final String TAG ="ServiceConnection";
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIMyService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//通过服务端onBind方法返回的binder对象得到IMyService的实例，得到实例就可以调用它的方法了
            mIMyService = IMyService.Stub.asInterface(service);

            try {
                Log.i(TAG,"setCallback to service");
                mIMyService.setCallback(new CallbackService());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            Log.i(TAG, "onServiceConnected service" + service);
            try {
                String gotStudentId = mIMyService.getStudentId("test");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            try {
                Student student = mIMyService.getStudent().get(0);
                showDialog(student.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.i("onServiceConnected", "onServiceConnected");


            Log.i("Client","______________________________");
            try {
                Student student = new Student();
                student.name = "client name";
                student.age = 10;
                student.sex = 10;
                student.sno = 10;
                Log.i("Client","before getConvertName_In "+student);
                mIMyService.getConvertName_In(student);
                Log.i("Client","after getConvertName_In "+student);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            Log.i("Client","______________________________");
            try {
                Student student = new Student();
                student.name = "client name";
                student.age = 10;
                student.sex = 10;
                student.sno = 10;
                Log.i("Client","before getStudentInfo_Out "+student);
                mIMyService.getStudentInfo_Out(student);
                Log.i("Client","after getStudentInfo_Out "+student);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            Log.i("Client","______________________________");
            try {
                Student student = new Student();
                student.name = "client name";
                student.age = 10;
                student.sex = 10;
                student.sno = 10;
                Log.i("Client","before getStudengInfo_Inout "+student);
                mIMyService.getStudengInfo_Inout(student);
                Log.i("Client","after getStudengInfo_Inout "+student);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void onBindingDied(ComponentName name) {
           Log.i("client onBindingDied","onBindingDied");
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.i("client onNullBinding","onNullBinding");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.button1) {
                    Intent intentService = new Intent();
                    ComponentName componentName = new ComponentName(
                            "com.example.myaidl.service",
                            "com.ryg.sayhi.aidl.MyService");
                    intentService.setComponent(componentName);
                    //startService(intentService);
                   MainActivity.this.bindService(intentService, mServiceConnection, BIND_AUTO_CREATE);
                }
            }
        });
    }

    public void showDialog(String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("scott")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        if (mIMyService != null) {
            unbindService(mServiceConnection);
        }
        super.onDestroy();

    }

}