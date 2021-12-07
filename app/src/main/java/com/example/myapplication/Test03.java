package com.example.myapplication;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Test03 {
    public <T extends ViewGroup> T get(Context context){
        return (T)new LinearLayout(context);
    }
}
