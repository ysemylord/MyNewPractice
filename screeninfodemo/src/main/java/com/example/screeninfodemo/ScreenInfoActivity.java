package com.example.screeninfodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;

import java.lang.reflect.Method;

public class ScreenInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_info);
       /* DisplayMetrics metrics = getResources().getDisplayMetrics();*/
        Display display = getWindowManager().getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
        Log.i("ScreenInfoActivity resolution", metrics.widthPixels + "*" + metrics.heightPixels);
        Log.i("ScreenInfoActivity densityDpi", metrics.densityDpi + "");//可能不准确
        Log.i("ScreenInfoActivity xdpi", metrics.xdpi + "");//可能不准确
        Log.i("ScreenInfoActivity ydpi", metrics.ydpi + "");//可能不准确
        Log.i("ScreenInfoActivity density", metrics.density + "");//可能不准确
    }
}