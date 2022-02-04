package com.example.customview.MyLayoutInflaterFactory

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import java.util.jar.Attributes

/**
 * 为了在MyLayoutInflaterFactory更好的识别，创建的View
 */
class InflaterTagView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? ): AppCompatEditText(context,attributes) {
}