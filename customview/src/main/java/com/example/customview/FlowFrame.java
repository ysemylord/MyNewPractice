package com.example.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FlowFrame extends ViewGroup {

    //保存每行的View
    private List<List<View>> allLinesViews = new ArrayList<>();

    //保存每行的高度
    private List<Integer> allLinesHeights = new ArrayList<>();

    int horizontalPadding = 20;
    int verticalPadding = 20;

    public FlowFrame(Context context) {
        super(context);
    }

    public FlowFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowFrame(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        allLinesViews.clear();
        allLinesHeights.clear();

        //
        int lineWidthUsed = 0;
        int lineHeightUsed = 0;
        int maxWidth = 0;
        int maxHeight = 0;


        int viewGroupSuggestWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewGroupSuggestHeight = MeasureSpec.getSize(heightMeasureSpec);

        List<View> oneLineViews = new ArrayList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            //测量child的大小
            View view = getChildAt(i);
            LayoutParams layoutParams = view.getLayoutParams();
            int widthPadding = getPaddingLeft() + getPaddingRight();
            int heightPadding = getPaddingTop() + getPaddingBottom();
            view.measure(
                    getChildMeasureSpec(widthMeasureSpec, widthPadding, layoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, heightPadding, layoutParams.height)
            );
            int childMeasuredWidth = view.getMeasuredWidth();
            int childMeasuredHeight = view.getMeasuredHeight();


            //FlowFrame的MeasureSpec mode 为AT_MOST时 ,FlowFrame的高宽

            //如果超过了一行
            if(lineWidthUsed + childMeasuredWidth + horizontalPadding > viewGroupSuggestWidth){
                //换行 更新测量触的FlowFrame的高宽
                maxWidth = Math.max(maxWidth, lineWidthUsed);
                maxHeight = maxHeight + lineHeightUsed + verticalPadding;
                allLinesViews.add(oneLineViews);
                allLinesHeights.add(lineHeightUsed);
                oneLineViews = new ArrayList<>();
                lineWidthUsed=0;
                lineHeightUsed=0;
            }

            lineWidthUsed += childMeasuredWidth + horizontalPadding;
            lineHeightUsed = Math.max(lineHeightUsed, childMeasuredHeight);
            oneLineViews.add(view);

            //处理最后一行
            if (i == childCount - 1) {
                allLinesViews.add(oneLineViews);
                allLinesHeights.add(lineHeightUsed);
            }

        }

        //计算出的allWidth和allHeight，其实是FlowFrame的
        // mode为 MeasureSpec.AT_MOST时的高宽
        //设置时，还要根据考虑mode为MeasureSpec.EXACTLY/UNSPECIFIED

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measuredWidth = widthMode == MeasureSpec.EXACTLY ? viewGroupSuggestWidth : maxWidth;
        int measuredHeight = heightMode == MeasureSpec.EXACTLY ? viewGroupSuggestHeight : maxHeight;

        setMeasuredDimension(measuredWidth, measuredHeight);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        for (int i = 0; i < allLinesViews.size(); i++) {
            List<View> oneLineViews = allLinesViews.get(i);

            //布局低i行的child
            for (int j = 0; j < oneLineViews.size(); j++) {
                View view = oneLineViews.get(j);
                //对Child布局
                view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
                left = left + view.getMeasuredWidth() + horizontalPadding;
            }

            left = getPaddingLeft();
            top = top + allLinesHeights.get(i);
        }
    }
}
