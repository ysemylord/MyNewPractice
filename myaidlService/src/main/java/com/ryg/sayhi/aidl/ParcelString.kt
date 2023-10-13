package com.ryg.sayhi.aidl

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ParcelString(val desc:String):Parcelable{
    override fun toString(): String {
        return desc
    }
}