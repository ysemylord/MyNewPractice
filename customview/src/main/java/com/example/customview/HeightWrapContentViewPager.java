package com.example.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class HeightWrapContentViewPager extends ViewPager {
    public HeightWrapContentViewPager(@NonNull Context context) {
        super(context);
    }

    public HeightWrapContentViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("MyViewPager width", getDefaultSize(0, widthMeasureSpec) + "");
        Log.i("MyViewPager height", getDefaultSize(0, heightMeasureSpec) + "");

        //计算child中最大的高度
        int maxWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            child.measure(
                    getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), layoutParams.height)
            );

            maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
        }

        super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(maxHeight,MeasureSpec.EXACTLY)
        );
    }
}
