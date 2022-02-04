package com.example.jetpackdemo.advance_ui.draw_fish

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout

import java.util.jar.Attributes
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class FinishContainer(context: Context, attributeSet: AttributeSet?) :
    RelativeLayout(context, attributeSet) {

    private var path: Path? = null
    val fish by lazy {
        DrawFishDrawable().apply { start() }
    }

    init {
        setWillNotDraw(false)

        addView(
            ImageView(context).apply {
                setImageDrawable(fish)
            },
            LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val touchX = event.x
                val touchY = event.y

                val touchPoint = PointF(touchX, touchY)

                //鱼的重心坐标
                val fishMiddlePointF = PointF(fish.middlePoint.x + x, fish.middlePoint.y + y)

                //鱼头坐标
                val fishHeadPointF = PointF(fish.headCenterPoint.x + x, fish.headCenterPoint.y + y)

                val control2PointF = calculatePoint(fishHeadPointF, 600f, 60f)

                path = Path().apply {
                    moveTo(fishMiddlePointF.x, fishMiddlePointF.y)
                    cubicTo(
                        fishHeadPointF.x,
                        fishHeadPointF.y,
                        control2PointF.x,
                        control2PointF.y,
                        touchPoint.x,
                        touchPoint.y
                    )
                }
                invalidate()

            } else if (event.action == MotionEvent.ACTION_DOWN) {
                performClick()
            }
            false
        }
    }

    val paint = Paint().apply {
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path?.run {
            canvas.drawPath(this, paint)
        }
    }

    fun calculatePoint(startPoint: PointF, length: Float, angle: Float): PointF {
        //cos/sin的参数是弧度，所以要先将角度转为弧度
        val dx = cos(Math.toRadians(angle.toDouble())) * length
        //因为三角函数的坐标系的y轴和android的坐标系的y轴是相反的，所以sin这里减去一个180度，或者值取负也行
        val dy = sin(Math.toRadians(angle.toDouble() - 180)) * length
        return PointF(startPoint.x + dx.toFloat(), startPoint.y + dy.toFloat())
    }

    fun includeAngle(o: PointF, a: PointF, b: PointF): Float {
        val 向量OA乘向量OB = (a.x - o.x) * (b.x - o.x) + (a.y - o.y) * (b.y - o.y)
        val OA的模乘 = sqrt((a.x - o.x) * (a.x - o.x) + (a.y - o.y) * (a.y - o.y))
        val OB的模乘 = sqrt((b.x - o.x) * (b.x - o.x) + (b.y - o.y) * (b.y - o.y))
        val cosAOB = 向量OA乘向量OB / (OA的模乘 * OB的模乘)
        val angleAOB = acos(cosAOB)
        return angleAOB
    }

}