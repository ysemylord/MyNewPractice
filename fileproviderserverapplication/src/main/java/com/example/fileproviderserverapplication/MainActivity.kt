package com.example.fileproviderserverapplication

import android.content.ComponentName
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

/**
 * client 通过FileProvider分享文件
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val file = File(filesDir,"test.txt").apply { writeText("123") }


    }

    fun ski(view: View) {
        val file = File(filesDir,"test.txt").apply { writeText("123") }
        MediaScannerConnection.scanFile(this, arrayOf(file.absolutePath), arrayOf("text/plain")
        ) { path, uri ->
            Log.i("MediaScannerConnection",Thread.currentThread().name)
            skip()
        }
    }

    private fun skip() {
        val file = File(filesDir, "test.txt")
        val uri = FileProvider.getUriForFile(
            this,
            "com.ysx.fileproviderserver.fileprovider",
            file
        )
        val intent = Intent().apply {
            component = ComponentName(
                "com.example.fileproviderclientapplication",
                "com.example.fileproviderclientapplication.MainActivity"
            )
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            setDataAndType(uri, contentResolver.getType(uri))
        }
        startActivity(intent)
    }
}