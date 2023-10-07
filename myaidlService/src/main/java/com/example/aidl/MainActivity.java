package com.example.aidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.ryg.sayhi.aidl.IMyService;
import com.ryg.sayhi.aidl.Student;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

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
            Log.i(TAG, "onServiceConnected service" + service);
            try {
                String gotStudentId = mIMyService.getStudentId("test");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            try {
                Student student = mIMyService.getStudent().get(0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.i("client onServiceConnected", "onServiceConnected");
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


    public void bindService(View view) {
        Intent intentService = new Intent();
        ComponentName componentName = new ComponentName(
                "com.example.myaidl.service",
                "com.ryg.sayhi.aidl.MyService");
        intentService.setComponent(componentName);
        startService(intentService);
    }
}