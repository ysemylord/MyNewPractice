package com.example.mediabrowserapplication

import android.content.ComponentName
import android.media.MediaMetadata
import android.media.browse.MediaBrowser
import android.media.session.MediaController
import android.media.session.PlaybackState
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var mediaBrowser: MediaBrowser
    lateinit var myMediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaBrowser = MediaBrowser(
            this,
            ComponentName(this, MyMediaService::class.java),
            object : MediaBrowser.ConnectionCallback() {
                //客户端和服务端连接上了
                override fun onConnected() {
                    super.onConnected()

                    val token = mediaBrowser.sessionToken
                    myMediaController = MediaController(baseContext, token)

                    Log.i("MediaBrowser", "onConnected,now media is: ${myMediaController.metadata?.getString(MediaMetadata.METADATA_KEY_TITLE)}")

                    myMediaController.registerCallback(object : MediaController.Callback() {

                        override fun onMetadataChanged(metadata: MediaMetadata?) {
                            super.onMetadataChanged(metadata)
                            Log.i("MediaBrowser", "data changed from service $metadata")
                        }

                        override fun onPlaybackStateChanged(state: PlaybackState?) {
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
    }
}