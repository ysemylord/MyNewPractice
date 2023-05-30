package com.example.mediabrowserapplication

import android.media.MediaMetadata
import android.media.session.PlaybackState.STATE_PAUSED
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat

class TunerRadioService : MediaBrowserServiceCompat() {

    lateinit var mediaSession: MediaSessionCompat

    companion object {
         val TAG: String = TunerRadioService::class.java.simpleName
        const val ROOT_ID ="ROOT_ID"
    }

    val songInfos = mutableListOf<String>("first music", "second music", "third music")
    var currentIndex = 0
    private val mediaSessionCompatCallback = object : MediaSessionCompat.Callback() {

        init {

            /*//告诉客户端，当前歌曲的信息和播放状态
            mediaSession.setMetadata(
                MediaMetadataCompat.Builder()
                    .putString(MediaMetadata.METADATA_KEY_TITLE, songInfos[currentIndex])
                    .build()
            )

            mediaSession.setPlaybackState(
                PlaybackStateCompat.Builder()
                    .setState(STATE_PAUSED, 0, 1.0f, SystemClock.elapsedRealtime())
                    .build()
            )*/
        }

        override fun onPlay() {
            super.onPlay()
            Log.i(TAG, "收到客户端的请求，开始play")
            mediaSession.setPlaybackState(
                PlaybackStateCompat.Builder()
                    .setState(
                        PlaybackStateCompat.STATE_PLAYING,
                        0,
                        1.0f,
                        SystemClock.elapsedRealtime()
                    )
                    .build()
            )
        }

        override fun onPause() {
            super.onPause()
            Log.i(TAG, "收到客户端的请求，停止")
            mediaSession.setPlaybackState(
                PlaybackStateCompat.Builder()
                    .setState(
                        PlaybackStateCompat.STATE_PAUSED,
                        0,
                        1.0f,
                        SystemClock.elapsedRealtime()
                    )
                    .build()
            )
        }


        override fun onSkipToNext() {
            super.onSkipToNext()
            Log.i(TAG, "收到客户端的请求，下一首")
            currentIndex = (currentIndex + 1) % songInfos.size

            mediaSession.setMetadata(
                MediaMetadataCompat.Builder()
                    .putString(MediaMetadata.METADATA_KEY_TITLE, songInfos[currentIndex])
                    .build()
            )

        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
            Log.i(TAG, "收到客户端的请求，上一首")
            currentIndex = (currentIndex - 1 + songInfos.size) % songInfos.size
            mediaSession.setMetadata(
                MediaMetadataCompat.Builder()
                    .putString(MediaMetadata.METADATA_KEY_TITLE, songInfos[currentIndex])
                    .build()
            )
        }
    }


    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
        mediaSession = MediaSessionCompat(this, TAG)

        sessionToken = mediaSession.sessionToken

        // 客户端想服务端发送命令，服务端就会收到相应的回调
        mediaSession.setCallback(mediaSessionCompatCallback)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return BrowserRoot(ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        Log.i(TAG, "onLoadChildren")
        result.detach()
        val mediaItems = transformPlayList()
        result.sendResult(mediaItems)
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
    }

    fun transformPlayList(): ArrayList<MediaBrowserCompat.MediaItem> {
        //我们模拟获取数据的过程，真实情况应该是异步从网络或本地读取数据
        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "" + 0)
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "四个女生")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "心愿")
            .build()
        val metadata2 = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "" +1)
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "foxtail_grass")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "风舞")
            .build()
        val metadata3 = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "" + 2)
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "两只老虎")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "群星")
            .build()
        val metadata4 = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "" + 3)
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "采蘑菇的小姑娘")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "群星")
            .build()
        val mediaItems = ArrayList<MediaBrowserCompat.MediaItem>()
        mediaItems.add(createMediaItem(metadata))
        mediaItems.add(createMediaItem(metadata2))
        mediaItems.add(createMediaItem(metadata3))
        mediaItems.add(createMediaItem(metadata4))
        return mediaItems
    }

    private fun createMediaItem(metadata: MediaMetadataCompat): MediaBrowserCompat.MediaItem {
        return MediaBrowserCompat.MediaItem(
            metadata.description,
            MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
        )
    }
}