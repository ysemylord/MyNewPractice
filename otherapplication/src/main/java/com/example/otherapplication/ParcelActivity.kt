package com.example.otherapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

class ParcelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parcel)
        val parcel = Parcel.obtain()
        parcel.writeInt(19)
        parcel.writeString("1222")
        //val bs = parcel.marshall()
        val age = parcel.readInt()
        val str = parcel.readString()

        parcel.setDataPosition(0)
        parcel.recycle()


    }
}

class User(var name: String?, var age: Int) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            val name = parcel.readString()
            val age = parcel.readInt()
            return User(name, age)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}