package com.example.mediabrowserapplication

import android.content.ComponentName
import android.media.MediaMetadata
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import shared.logd

class MainActivity : AppCompatActivity() {
    lateinit var mediaBrowser: MediaBrowserCompat
    lateinit var myMediaController: MediaControllerCompat
    val subscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {

        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            super.onChildrenLoaded(parentId, children)
            Log.i(
                TAG,
                "加载的媒体子项为${children}"
            )
        }

        //注意，下面这个方法不会被调用
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>,
            options: Bundle
        ) {
            super.onChildrenLoaded(parentId, children, options)
        }

        override fun onError(parentId: String, options: Bundle) {
            super.onError(parentId, options)
        }
    }
    val mediarInfoTextView by lazy {
        findViewById<TextView>(R.id.meida_info)
    }
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaBrowser = MediaBrowserCompat(
            this,
            //ComponentName(this, MyMediaService::class.java),
            ComponentName(this, TunerRadioService::class.java),
            object : MediaBrowserCompat.ConnectionCallback() {
                //客户端和服务端连接上了
                override fun onConnected() {
                    super.onConnected()
                    Log.i(TAG, "onConnected")
                    if (mediaBrowser.isConnected) {
                        val parentId = mediaBrowser.root
                        mediaBrowser.unsubscribe(parentId)
                        mediaBrowser.subscribe(parentId, subscriptionCallback)
                        getController()
                    }

                }

                private fun getController() {
                    val token = mediaBrowser.sessionToken
                    myMediaController = MediaControllerCompat(baseContext, token)

                    Log.i(
                        "MediaBrowser",
                        "onConnected,now media is: ${
                            myMediaController.metadata?.getString(MediaMetadata.METADATA_KEY_TITLE)
                        }"
                    )

                    myMediaController.registerCallback(object :
                        MediaControllerCompat.Callback() {

                        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
                            super.onMetadataChanged(metadata)
                            Log.i("MediaBrowser", "data changed from service $metadata")

                        }

                        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                            super.onPlaybackStateChanged(state)
                            Log.i("MediaBrowser", "state changed from service $state")

                        }
                    })
                }

                override fun onConnectionSuspended() {
                    super.onConnectionSuspended()
                    Log.i("MediaBrowser", "onConnectionSuspended")
                }

                override fun onConnectionFailed() {
                    super.onConnectionFailed()
                    Log.i("MediaBrowser", "onConnectionFailed")

                }
            },
            null
        ).also {
            it.connect()
        }

        // test()
    }

    private fun test() {
        ViewModelProvider(this).get<MainViewModel>().also {
            it.createMediaBrowser(this)
            it.setMediaControlCompatCallBack(object : MediaControllerCompat.Callback() {

                /**
                 * 状态变化
                 */
                override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                    super.onPlaybackStateChanged(state)
                    "onPlaybackStateChanged --->".logd()

                }

                /**
                 * 数据变化
                 */
                override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
                    super.onMetadataChanged(metadata)
                    "onMetadataChanged --->".logd()

                }
            })
        }
    }

    fun previous(view: View) {
        myMediaController.transportControls.skipToPrevious()
    }

    fun next(view: View) {
        myMediaController.transportControls.skipToNext()
    }

    fun play(view: View) {
        myMediaController.transportControls.play()
    }

    fun pause(view: View) {
        myMediaController.transportControls.pause()
    }

}