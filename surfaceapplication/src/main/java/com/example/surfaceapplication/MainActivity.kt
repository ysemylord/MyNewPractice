package com.example.surfaceapplication

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import java.io.IOException
import java.nio.ByteBuffer
import java.util.concurrent.ArrayBlockingQueue

class MainActivity : AppCompatActivity() {

    private var svDecode: SurfaceView? = null
    private var svEncode: PaintView? = PaintView(this,null)
    private var mediaCodec: MediaCodec? = null
    private  var mediaDecode:MediaCodec? = null
    private var inputSurface: Surface? = null
    private var stop: Button? = null
    private val TAG = "yforyoung"
    private val mOutputDataQueue: ArrayBlockingQueue<ByteArray> = ArrayBlockingQueue(8)
    private val mediaFormat: MediaFormat = MediaFormat.createVideoFormat("video/avc", 640, 480)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        //svEncode = findViewById(R.id.encode)
        //svDecode = findViewById(R.id.decode)
        stop = findViewById(R.id.stop)
        mediaFormat.setInteger(
            MediaFormat.KEY_COLOR_FORMAT,
            MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
        )
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 1024)
        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 2)
        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 25)

//设置surface
        inputSurface = MediaCodec.createPersistentInputSurface()
        svEncode?.setSurface(inputSurface!!)
        //surface创建
        svEncode?.setVisibility(View.VISIBLE)
        svDecode?.setVisibility(View.VISIBLE)
       /* stop.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View?) {
                mediaCodec.stop()
                mediaDecode.stop()
                mediaDecode.release()
                mediaCodec.release()
            }
        })*/
        svEncode?.getHolder()?.addCallback(object : SurfaceHolder.Callback {

            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.i(TAG, "surfaceCreated: ")
                encode() //开始编码
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {

            }
        })

        svDecode?.getHolder()?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.i(TAG, "surfaceCreated: ")
                decode(holder.getSurface())
                //解码，传入用于显示画面的surface
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }

        })


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun encode() {
        try {
            mediaCodec = MediaCodec.createEncoderByType("video/avc")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaCodec?.setCallback(object:MediaCodec.Callback(){
            override fun onInputBufferAvailable(codec: MediaCodec, index: Int) {
            }

            override fun onOutputBufferAvailable(
                codec: MediaCodec,
                index: Int,
                info: MediaCodec.BufferInfo
            ) {
                val outputBuffer: ByteBuffer? = codec.getOutputBuffer(index)
                //这里将编码后的流存入byte[]队列，也可以在这里将画面输出到文件或者发送到远端
                if (outputBuffer != null && info.size > 0) {
                    val buffer = ByteArray(outputBuffer.remaining())
                    outputBuffer.get(buffer)
                    val result: Boolean = mOutputDataQueue.offer(buffer)
                    Log.i(TAG, "onOutputBufferAvailable: offer")
                    if (!result) {
                        Log.e(TAG, "onOutputBufferAvailable: offer to queue failed")
                    }
                }
                codec.releaseOutputBuffer(index, false)
                if (info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM !== 0) {
                    mediaCodec?.release()
                }
            }

            override fun onError(codec: MediaCodec, e: MediaCodec.CodecException) {

            }

            override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) {

            }

        })

        mediaCodec?.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        mediaCodec?.setInputSurface(inputSurface!!) //设置输入surface必须在configure之后，start之前
        mediaCodec?.start()
    }

    private fun decode(surface: Surface) {
        try {
            mediaDecode = MediaCodec.createDecoderByType("video/avc")
            mediaCodec?.setCallback(object :MediaCodec.Callback(){
                override fun onInputBufferAvailable(codec: MediaCodec, i: Int) {
                    //从队列中获取视频流的byte[] 解码成画面显示到surface
                    val byteBuffer: ByteBuffer? = mediaCodec?.getInputBuffer(i)
                    byteBuffer?.clear()
                    val dataSource: ByteArray = mOutputDataQueue.poll()
                    var length = 0
                    if (dataSource != null) {
                        byteBuffer?.put(dataSource)
                        length = dataSource.size
                    }
                    mediaDecode?.queueInputBuffer(i, 0, length, 0, 0)
                }

                override fun onOutputBufferAvailable(
                    codec: MediaCodec,
                    i: Int,
                    bufferInfo: MediaCodec.BufferInfo
                ) {
                    val outputBuffer: ByteBuffer? = mediaDecode?.getOutputBuffer(i)
                    if (outputBuffer != null && bufferInfo.size > 0) {
                        val buffer = ByteArray(outputBuffer.remaining())
                        outputBuffer.get(buffer)
                    }
                    mediaDecode?.releaseOutputBuffer(i, true)
                }

                override fun onError(codec: MediaCodec, e: MediaCodec.CodecException) {

                }

                override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) {
                }

            })


            mediaDecode?.configure(mediaFormat, surface, null, 0)
            mediaDecode?.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }



}