package com.example.customview.view_life_cycle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.customview.R;

public class LifecycleTestView extends androidx.appcompat.widget.AppCompatTextView {

    final String TAG = "LifecycleTestView";

    public LifecycleTestView(Context context) {
        super(context);
        Log.i(TAG, "contructor 1");
    }

    public LifecycleTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "contructor 2");
    }

    public LifecycleTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }



    @Override
    public boolean isAttachedToWindow() {
        Log.i(TAG, "isAttachedToWindow");
        return super.isAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.i(TAG, "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }
}
