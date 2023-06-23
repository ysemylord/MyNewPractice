package com.example.maidlclient;


import android.app.Activity;
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

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIMyService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//通过服务端onBind方法返回的binder对象得到IMyService的实例，得到实例就可以调用它的方法了
            mIMyService = IMyService.Stub.asInterface(service);
            try {

                long startTime = System.currentTimeMillis();
                String gotStudentId = mIMyService.getStudentId("test");
                Log.i("client","cost time "+ (System.currentTimeMillis()-startTime));

                startTime = System.currentTimeMillis();
                mIMyService.addStudentName("jack");
                Log.i("client","cost time "+ (System.currentTimeMillis()-startTime));

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            try {
                Student student = mIMyService.getStudent().get(0);
                showDialog(student.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.i("client onServiceConnected", "onServiceConnected");
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.i("client onBindingDied", "onBindingDied");
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.i("client onNullBinding", "onNullBinding");

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
                    intentService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intentService.setPackage("com.example.myaidl.service");
                    intentService.setClassName("com.example.myaidl.service","com.ryg.sayhi.aidl.MyService");
                    MainActivity.this.bindService(intentService, mServiceConnection, BIND_AUTO_CREATE);
                }
            }
        });
        GetServiceManagerTestKt.testBatteryService(this);
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