package com.example.jetpackdemo.advance_ui.span_string;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

/***
 * author: hu
 */
public class ForLineStraceImageSpan extends ImageSpan {


    public ForLineStraceImageSpan(@NonNull Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        canvas.save();

        //假设lineExtra为10
        int lineExtra = 10;
        int transY = bottom - b.getBounds().bottom- lineExtra;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            transY -= paint.getFontMetricsInt().descent;
        } else if (mVerticalAlignment == ALIGN_CENTER) {
            transY = top + (bottom - top) / 2 - b.getBounds().height() / 2;
        }

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }


}
