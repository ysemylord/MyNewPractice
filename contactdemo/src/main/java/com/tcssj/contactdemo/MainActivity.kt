package com.tcssj.contactdemo

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Profile
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivityMainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(android.Manifest.permission.READ_CONTACTS),
                1
            )
        }
        setContentView(R.layout.activity_main)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        test(null)
    }

    fun test(view: View?) {
        // 通过ContentResolver获取数据
        val contentResolver = contentResolver


// 创建查询的Projection和Selection语句
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val selection =
            ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?"
        val selectionArgs = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE.toString()
        )

// 查询数据
        val cursor =
            contentResolver.query(Profile.CONTENT_URI, projection, selection, selectionArgs, null)

// 遍历结果集并获取电话号码
        if (cursor != null && cursor.moveToFirst()) {
            val numberColumnIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val phoneNumber = cursor.getString(numberColumnIndex)

            // 处理电话号码
            Log.d("Phone Number", phoneNumber)

            // 关闭结果集
            cursor.close()
        }

    }
}