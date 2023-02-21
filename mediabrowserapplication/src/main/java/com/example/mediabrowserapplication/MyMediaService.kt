package com.example.mediabrowserapplication

import android.media.MediaMetadata
import android.media.browse.MediaBrowser
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.media.session.PlaybackState.STATE_PAUSED
import android.os.Bundle
import android.os.SystemClock
import android.service.media.MediaBrowserService
import android.util.Log

class MyMediaService : MediaBrowserService() {

    lateinit var mediaSession: MediaSession

    companion object {
        const val TAG = "MyMediaService"
    }

    val songInfos = listOf<String>("first music", "second music", "third music")
    var  currentIndex = 0

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG,"oncreate")
        mediaSession = MediaSession(this, "MediaService")

        sessionToken = mediaSession.sessionToken


        // 客户端想服务端发送命令，服务端就会收到相应的回调
        mediaSession.setCallback(object : MediaSession.Callback() {


            init {

                //告诉客户端，当前歌曲的信息和播放状态
                mediaSession.setMetadata(
                    MediaMetadata.Builder().putString(MediaMetadata.METADATA_KEY_TITLE, songInfos[currentIndex]).build()
                )

                mediaSession.setPlaybackState(
                    PlaybackState.Builder()
                        .setState(STATE_PAUSED, 0, 1.0f, SystemClock.elapsedRealtime())
                        .build()
                )
            }

            override fun onPlay() {
                super.onPlay()
                Log.i(TAG, "收到客户端的请求，开始play")
                mediaSession.setPlaybackState(
                    PlaybackState.Builder()
                        .setState(STATE_PAUSED, 0, 1.0f, SystemClock.elapsedRealtime())
                        .build()
                )
            }

            override fun onPause() {
                super.onPause()
                Log.i(TAG, "收到客户端的请求，停止")
                mediaSession.setPlaybackState(
                    PlaybackState.Builder()
                        .setState(STATE_PAUSED, 0, 1.0f, SystemClock.elapsedRealtime())
                        .build()
                )
            }


            override fun onSkipToNext() {
                super.onSkipToNext()
                Log.i(TAG, "收到客户端的请求，下一首")
                currentIndex = (currentIndex + 1) % songInfos.size

                mediaSession.setMetadata(
                    MediaMetadata.Builder().putString(MediaMetadata.METADATA_KEY_TITLE, songInfos[currentIndex]).build()
                )

            }

            override fun onSkipToPrevious() {
                super.onSkipToPrevious()
                Log.i(TAG, "收到客户端的请求，上一首")
                currentIndex = (currentIndex - 1 + songInfos.size) % songInfos.size
                mediaSession.setMetadata(
                    MediaMetadata.Builder().putString(MediaMetadata.METADATA_KEY_TITLE, songInfos[currentIndex]).build()
                )
            }
        })
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot("MyMedia", null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowser.MediaItem>>
    ) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
    }
}