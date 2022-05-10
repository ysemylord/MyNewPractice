package com.example.surfaceapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
public class MyActivity extends AppCompatActivity {

    private SurfaceView svDecode;
    private PaintView svEncode;
    private MediaCodec mediaCodec, mediaDecode;
    private Surface inputSurface;
    private Button stop;
    private String TAG = "yforyoung";
    private final static ArrayBlockingQueue<byte[]> mOutputDataQueue = new ArrayBlockingQueue<>(8);
    private MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc", 640, 480);


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        svDecode = findViewById(R.id.sv_decode);
        svEncode = findViewById(R.id.sv_encode);
        stop = findViewById(R.id.stop);

        mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 1024);
        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 2);
        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 25);

//设置surface
        inputSurface = MediaCodec.createPersistentInputSurface();
        svEncode.setSurface(inputSurface);
//surface创建
        svEncode.setVisibility(View.VISIBLE);
        svDecode.setVisibility(View.VISIBLE);


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaCodec.stop();
                mediaDecode.stop();
                mediaDecode.release();
                mediaCodec.release();
            }
        });

        svEncode.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.i(TAG, "surfaceCreated: ");
                encode();//开始编码
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            }
        });

        svDecode.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.i(TAG, "surfaceCreated: ");
                decode(surfaceHolder.getSurface());
                //解码，传入用于显示画面的surface
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

    }


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void encode() {
        try {
            mediaCodec = MediaCodec.createEncoderByType("video/avc");
        } catch (IOException e) {
            e.printStackTrace();
        }


        mediaCodec.setCallback(new MediaCodec.Callback() {
            @Override
            public void onInputBufferAvailable(MediaCodec codec, int index) {
            }

            @Override
            public void onOutputBufferAvailable(MediaCodec codec, int index, MediaCodec.BufferInfo info) {
                ByteBuffer outputBuffer = codec.getOutputBuffer(index);
                //这里将编码后的流存入byte[]队列，也可以在这里将画面输出到文件或者发送到远端
                if (outputBuffer != null && info.size > 0) {
                    byte[] buffer = new byte[outputBuffer.remaining()];
                    outputBuffer.get(buffer);
                    boolean result = mOutputDataQueue.offer(buffer);
                    Log.i(TAG, "onOutputBufferAvailable: offer");
                    if (!result) {
                        Log.e(TAG, "onOutputBufferAvailable: offer to queue failed");
                    }
                }
                codec.releaseOutputBuffer(index, false);
                if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    mediaCodec.release();
                }
            }

            @Override
            public void onError(MediaCodec codec, MediaCodec.CodecException e) {
            }

            @Override
            public void onOutputFormatChanged(MediaCodec codec, MediaFormat format) {
            }
        });

        mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mediaCodec.setInputSurface(inputSurface);//设置输入surface必须在configure之后，start之前
        mediaCodec.start();
    }

    private void decode(Surface surface) {
        try {
            mediaDecode = MediaCodec.createDecoderByType("video/avc");

            mediaDecode.setCallback(new MediaCodec.Callback() {
                @Override
                public void onInputBufferAvailable(MediaCodec mediaCodec, int i) {
                    //从队列中获取视频流的byte[] 解码成画面显示到surface
                    ByteBuffer byteBuffer = mediaCodec.getInputBuffer(i);
                    byteBuffer.clear();
                    byte[] dataSource = mOutputDataQueue.poll();
                    int length = 0;
                    if (dataSource != null) {
                        byteBuffer.put(dataSource);
                        length = dataSource.length;
                    }
                    mediaDecode.queueInputBuffer(i, 0, length, 0, 0);

                }

                @Override
                public void onOutputBufferAvailable(MediaCodec mediaCodec, int i, MediaCodec.BufferInfo bufferInfo) {
                    ByteBuffer outputBuffer = mediaDecode.getOutputBuffer(i);
                    if (outputBuffer != null && bufferInfo.size > 0) {
                        byte[] buffer = new byte[outputBuffer.remaining()];
                        outputBuffer.get(buffer);
                    }
                    mediaDecode.releaseOutputBuffer(i, true);

                }

                @Override
                public void onError(MediaCodec mediaCodec, MediaCodec.CodecException e) {

                }

                @Override
                public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {

                }
            });

            mediaDecode.configure(mediaFormat, surface, null, 0);
            mediaDecode.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
