package com.example.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TestView1 extends TextView {
    public TestView1(Context context) {
        super(context);
        Log.i("TestView1","contructor 1");
    }

    public TestView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i("TestView1","contructor 2");
        for(int i=0;i<attrs.getAttributeCount();i++){
            Log.d("TestView1", attrs.getAttributeName(i)+"   :   "+attrs.getAttributeValue(i));
        }
    }

    public TestView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr,R.style.My_Style3);
    }

    public TestView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
