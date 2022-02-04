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
class DrawTextGradientCenter constructor(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    var percent = 0f
        set(value) {
            invalidate()
            field = value
        }


    private val paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        strokeWidth = 10f
        textSize = 50f
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), paint)
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, paint)


        drawBackText(canvas)
        drawFrontText(canvas)
    }


    private fun drawBackText(canvas: Canvas) {
        canvas.save()
        paint.textSize = 80f
        val fontMetrics = paint.fontMetrics
        val text = "合家欢乐"
        val textWidth = paint.measureText(text)

        val textLeft = (canvas.width - paint.measureText(text)) / 2f

        canvas.clipRect(textLeft + textWidth * percent, 0f, textLeft + textWidth, height.toFloat())

        canvas.drawText(
            text,
            textLeft,
            height / 2f + fontMetrics.ascent.absoluteValue - (fontMetrics.ascent.absoluteValue + fontMetrics.descent.absoluteValue) / 2,
            paint.apply {
                color = Color.BLACK
            })
        canvas.restore()

    }


    private fun drawFrontText(canvas: Canvas) {
        canvas.save()
        paint.textSize = 80f
        val fontMetrics = paint.fontMetrics
        val text = "合家欢乐"
        val textWidth = paint.measureText(text)
        val textLeft = (canvas.width - textWidth) / 2f

        canvas.clipRect(0f, 0f, textLeft + textWidth * percent, height.toFloat())

        canvas.drawText(
            text,
            textLeft,
            height / 2f + fontMetrics.ascent.absoluteValue - (fontMetrics.ascent.absoluteValue + fontMetrics.descent.absoluteValue) / 2,
            paint.apply {
                color = Color.RED
            })

        canvas.restore()

    }
}