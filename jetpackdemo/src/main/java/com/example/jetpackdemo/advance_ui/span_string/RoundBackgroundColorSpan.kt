package com.example.jetpackdemo.advance_ui.span_string

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan

class RoundBackgroundColorSpan(
        private val bgColor: Int,
        private val textColor: Int,
        private val radius: Float,
        private val padding: Float,
        private val textSize:Float
    ) :
        ReplacementSpan() {
        override fun getSize(
            paint: Paint,
            text: CharSequence?,
            start: Int,
            end: Int,
            fm: Paint.FontMetricsInt?
        ): Int {
            val originTextSize = paint.textSize
            paint.textSize = textSize
            val size = (paint.measureText(text, start, end) + padding * 2).toInt()
            paint.textSize = originTextSize
            return size
        }

        override fun draw(
            canvas: Canvas,
            text: CharSequence,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
        ) {
            val originColor = paint.color
            val originTextSize = paint.textSize
            paint.color = bgColor
            paint.textSize = textSize

            val rectStartY =
                y.toFloat() - textSize - padding * 2
            val rectEndY = y.toFloat() + paint.fontMetrics.bottom
            val rectWidth = (paint.measureText(text, start, end) + padding * 2)
            canvas.drawRoundRect(
                RectF(
                    x,
                    rectStartY,
                    x + rectWidth,
                    rectEndY
                ), radius, radius, paint
            )
            paint.color = textColor

            canvas.drawText(
                text,
                start,
                end,
                (x + padding),
                (rectStartY + rectEndY) / 2 + paint.fontMetrics.bottom,
                paint
            )

            paint.run {
                color = originColor
                textSize = originTextSize
            }
        }
    }