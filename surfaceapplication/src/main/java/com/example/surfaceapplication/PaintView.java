package com.example.surfaceapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.jetbrains.annotations.NotNull;

public class PaintView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private float mX;
    private float mY;
    private Paint mPaint = null;
    private Path mPath = null;
    // 线程结束标志位
    boolean mLoop = true;
    SurfaceHolder mSurfaceHolder = null;
    Canvas mCanvas;


    public PaintView(Context context, AttributeSet arr) {
        super(context, arr);

        mSurfaceHolder = this.getHolder();//获取holder
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setFormat(PixelFormat.OPAQUE);//不透明
        //mSurfaceHolder.setFormat(PixelFormat.RGB_565);
        //mSurfaceHolder.setFormat(PixelFormat.RGBA_8888);
        this.setFocusable(true);
//        setZOrderOnTop(true);//放到最顶层
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.ROUND);  //圆头
        mPaint.setDither(true);//消除拉动，使画面圓滑
        mPaint.setStrokeJoin(Paint.Join.ROUND); //结合方式，平滑
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        new Thread(this).start();//启动线程
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        mLoop = false; //结束线程

    }


    @Override
    public void run() {
        while (mLoop == true) {
            Draw();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private Surface surface;
    private Canvas  canvas;

    // 绘图
    private void Draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            if (surface != null) {
                canvas = surface.lockCanvas(null);
            }


            mCanvas.drawPath(mPath, mPaint);
            if (canvas != null && surface!=null) {
                canvas.drawPath(mPath, mPaint);
            }



        } catch (Exception e) {
        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }

            if (canvas != null && surface!=null) {
                surface.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
        }
        //更新绘制  
        invalidate();
        return true;
    }

    //手指点下屏幕时调用  
    private void touchDown(MotionEvent event) {
        //隐藏之前的绘制  
//        mPath.reset();  //
        float x = event.getX();
        float y = event.getY();

        mX = x;
        mY = y;
        //mPath绘制的绘制起点  
        mPath.moveTo(x, y);
    }

    //手指在屏幕上滑动时调用  
    private void touchMove(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();

        final float previousX = mX;
        final float previousY = mY;

        final float dx = Math.abs(x - previousX);
        final float dy = Math.abs(y - previousY);

        //两点之间的距离大于等于3时，生成贝塞尔绘制曲线  
        if (dx >= 3 || dy >= 3) {
            //设置贝塞尔曲线的操作点为起点和终点的一半  
            float cX = (x + previousX) / 2;
            float cY = (y + previousY) / 2;

            //二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点  
            mPath.quadTo(previousX, previousY, cX, cY);

            //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值  
            mX = x;
            mY = y;
        }
    }

    private void setEraser() {
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);
//        mPaint.setColor(mPaintColor);  
        mPaint.setStrokeCap(Paint.Cap.ROUND);  //圆头
        mPaint.setDither(true);//消除拉动，使画面圓滑
        mPaint.setStrokeJoin(Paint.Join.ROUND); //结合方式，平滑
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void setSurface(@NotNull Surface inputSurface) {
          this.surface=inputSurface;
    }
}