package com.example.surfaceapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.Surface;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MyTextView extends androidx.appcompat.widget.AppCompatTextView {

    public Surface surface ;
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(() ->{
                    setText(String.valueOf(System.currentTimeMillis()));
                });
            }
        },0,16);

    }

    @Override
    public void onDraw(Canvas canvas){
        Canvas persistentCanvas = surface.lockCanvas(null);
        super.onDraw(persistentCanvas);
        super.onDraw(canvas);
        surface.unlockCanvasAndPost(persistentCanvas);
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }
}
