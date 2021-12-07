package com.example.myapplication.reflect_test

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
class UserParcelable(val name:String,val age:Int): Parcelable

class UserSerializable(val name:String):Serializable
