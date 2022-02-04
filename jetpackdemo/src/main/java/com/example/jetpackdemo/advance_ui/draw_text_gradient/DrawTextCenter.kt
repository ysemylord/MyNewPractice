package com.example.jetpackdemo.advance_ui.draw_text_gradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.absoluteValue

/**
 * 让文字居中
 */
class DrawTextCenter constructor(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    private val paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        strokeWidth = 10f
        textSize = 50f
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), paint)
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, paint)


        /*  canvas.drawText("大家好", width / 2f, height / 2f, paint.apply {
              color = Color.RED
              //这个对齐指的是和drawText中x的对齐方式
              textAlign = Paint.Align.LEFT
          })


          canvas.drawText("大家好", width / 2f, height / 2f + paint.fontSpacing, paint.apply {
              textAlign = Paint.Align.CENTER
          })

          canvas.drawText("大家好", width / 2f, height / 2f + paint.fontSpacing * 2, paint.apply {
              textAlign = Paint.Align.RIGHT
          })*/

        val fontMetrics = paint.fontMetrics

        canvas.drawText(
            "合家欢乐",
            width / 2f,
            height / 2f+fontMetrics.ascent.absoluteValue-(fontMetrics.ascent.absoluteValue+fontMetrics.descent.absoluteValue)    /2,
            paint.apply {
                textSize = 80f
                color = Color.GREEN
                textAlign = Paint.Align.CENTER
            })
    }
}