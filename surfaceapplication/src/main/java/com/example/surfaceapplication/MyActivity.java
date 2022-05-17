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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
public class MyActivity extends AppCompatActivity {

    private SurfaceView svDecode;
    //private PaintView svEncode;
    private MyTextView svEncode;
    private MediaCodec mediaCodec, mediaDecode;
    private Surface inputSurface;
    private Button stop;
    private String TAG = "yforyoung";
    private final static ArrayBlockingQueue<byte[]> mOutputDataQueue = new ArrayBlockingQueue<>(8);
    private MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc", 640, 480);
    private BufferedOutputStream outputStream;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(getFilesDir(),"mediacodecDemo.h264");
        if(file.exists()){
            file.delete();
        }
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (Exception e){
            e.printStackTrace();
        }

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

        svEncode.postDelayed(new Runnable() {
            @Override
            public void run() {
                encode();//开始编码
            }
        },5000);

      /*  svEncode.getHolder().addCallback(new SurfaceHolder.Callback() {
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
        });*/

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
            private long mFramerate=30;
            private int TIMEOUT_TIME = 10000;
            public byte[] mFirstFrameConfig;


            @Override
            public void onInputBufferAvailable(MediaCodec codec, int index) {
            }
            private long computePresentationTime(long frameIndex) {
                return 132 + frameIndex * 1000000 / mFramerate;
            }
            @Override
            public void onOutputBufferAvailable(MediaCodec codec, int index, MediaCodec.BufferInfo info) {
                ByteBuffer outputBuffer = codec.getOutputBuffer(index);
                //这里将编码后的流存入byte[]队列，也可以在这里将画面输出到文件或者发送到远端
                if (outputBuffer != null && info.size > 0) {
                    byte[] outData = new byte[outputBuffer.remaining()];
                    outputBuffer.get(outData);


                    				/*H264编码首帧，内部存有SPS和PPS信息，需要保留起来，然后，加在每个H264关键帧的前面。
							* 其中有个字段是flags，它有几种常量情况。
								flags = 4；End of Stream。
								flags = 2；首帧信息帧。
								flags = 1；关键帧。
								flags = 0；普通帧。*/
                    MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                    int outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, TIMEOUT_TIME);

                    while (outputBufferIndex >= 0) {


                        if (bufferInfo.flags == 2) {//首帧，记录信息
                            mFirstFrameConfig = new byte[bufferInfo.size];
                            mFirstFrameConfig = outData;
                        } else if (bufferInfo.flags == 1) {
                            byte[] keyframe = new byte[bufferInfo.size + mFirstFrameConfig.length];
                            System.arraycopy(mFirstFrameConfig, 0, keyframe, 0, mFirstFrameConfig.length);
                            System.arraycopy(outData, 0, keyframe, mFirstFrameConfig.length, outData.length);

                            try {
                                outputStream.write(keyframe, 0, keyframe.length);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                outputStream.write(outData, 0, outData.length);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        codec.releaseOutputBuffer(outputBufferIndex, false);
                        outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, TIMEOUT_TIME);

                    }
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
                    Log.i("mediaDecode","onInputBufferAvailable");

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
