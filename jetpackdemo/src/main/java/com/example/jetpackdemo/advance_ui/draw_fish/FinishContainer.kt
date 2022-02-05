package com.example.jetpackdemo.advance_ui.draw_fish

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import java.security.cert.PolicyNode
import kotlin.math.*

class FinishContainer(context: Context, attributeSet: AttributeSet?) :
    RelativeLayout(context, attributeSet) {

    private var path: Path? = null
    val fish by lazy {
        DrawFishDrawable().apply {
            start()
        }
    }

    val imageFish by lazy {
        ImageView(context).apply {
            // this.setBackgroundColor(Color.BLUE)
            setImageDrawable(fish)
        }
    }

    var controlPointF2: PointF? = null

    var touchPoint: PointF? = null


    var circlePaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
    }

    var circleRadius = 20f
    var circleAlpha = 0.2f

    init {
        setWillNotDraw(false)

        addView(
            imageFish,
            LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val touchX = event.x
                val touchY = event.y

                touchPoint = PointF(touchX, touchY)

                ValueAnimator.ofFloat(100f, 600f).run {
                    duration = 3000L
                    addUpdateListener {
                        circleRadius = it.animatedValue as Float
                        circleAlpha = 1 - it.animatedFraction
                        invalidate()
                    }
                    start()
                }


                //鱼的重心坐标
                val fishMiddlePointF =
                    PointF(fish.middlePoint.x + imageFish.x, fish.middlePoint.y + imageFish.y)

                //鱼头坐标
                val fishHeadPointF = PointF(
                    fish.headCenterPoint.x + imageFish.x,
                    fish.headCenterPoint.y + imageFish.y
                )

                //val control2PointF = calculatePoint(fishHeadPointF, 600f, 60f)

                //控制点坐标

                val angle1 = includeAngle(fishMiddlePointF, fishHeadPointF, touchPoint!!)
                val angle1_half = angle1 / 2
                val angle2 = includeAngle(
                    fishMiddlePointF, fishHeadPointF,
                    PointF(fishMiddlePointF.x + 1, fishMiddlePointF.y)
                )

                val angle = angle2 - angle1_half


                controlPointF2 =
                    calculatePoint(fishMiddlePointF, fish.head_radius * 1.6f, angle)



                path = Path().apply {
                    moveTo(
                        fishMiddlePointF.x - fish.middlePoint.x,
                        fishMiddlePointF.y - fish.middlePoint.y
                    )
                    cubicTo(
                        fishHeadPointF.x - fish.middlePoint.x,
                        fishHeadPointF.y - fish.middlePoint.y,
                        controlPointF2?.x ?: 0f - fish.middlePoint.x,
                        controlPointF2?.y ?: 0f - fish.middlePoint.y,
                        touchPoint!!.x - fish.middlePoint.x,
                        touchPoint!!.y - fish.middlePoint.y
                    )
                }
                val pathAnimator = ObjectAnimator.ofFloat(imageFish, "x", "y", path).apply {
                    duration = 3000L
                    start()
                }

                val pathMeasure = PathMeasure(path, false)
                val tan = floatArrayOf(0f, 0f)
                pathAnimator.addUpdateListener {
                    val fraction = it.animatedFraction
                    pathMeasure.getPosTan(pathMeasure.length * fraction, null, tan)
                    //因为数学的y坐标方向和android坐标系的y坐标方向是相反的，所以tan[1]要加符号
                    //angle 切线角度
                    val angleTan = Math.toDegrees(atan2(-tan[1].toDouble(), tan[0].toDouble()))
                    fish.startAngle = angleTan.toFloat()
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
        /*   path?.run {
               canvas.drawPath(this, paint)
           }

           controlPointF2?.run {
               canvas.drawCircle(x, y, 10f, Paint().apply { color = Color.GREEN })
           }*/

        touchPoint?.run {
            canvas.drawCircle(this.x.toFloat(), this.y, circleRadius, circlePaint.apply {
                alpha = (circleAlpha * 255).toInt()
            })
        }
    }

    fun calculatePoint(startPoint: PointF, length: Float, angle: Float): PointF {
        //cos/sin的参数是弧度，所以要先将角度转为弧度
        val dx = cos(Math.toRadians(angle.toDouble())) * length
        //因为三角函数的坐标系的y轴和android的坐标系的y轴是相反的，所以sin这里减去一个180度，或者值取负也行
        val dy = sin(Math.toRadians(angle.toDouble() - 180)) * length
        return PointF(startPoint.x + dx.toFloat(), startPoint.y + dy.toFloat())
    }

    /**
     * 计算角AOB
     */
    private fun includeAngle(o: PointF, a: PointF, b: PointF): Float {
        val 向量OA乘向量OB = (a.x - o.x) * (b.x - o.x) + (a.y - o.y) * (b.y - o.y)
        val OA的模乘 = sqrt((a.x - o.x) * (a.x - o.x) + (a.y - o.y) * (a.y - o.y))
        val OB的模乘 = sqrt((b.x - o.x) * (b.x - o.x) + (b.y - o.y) * (b.y - o.y))
        val cosAOB = 向量OA乘向量OB / (OA的模乘 * OB的模乘)
        val angleAOB = acos(cosAOB)
        //angleAOB的取值范围是[0,180]度，但是angleAOB也可能是负数的,[0, -180]

        //判断angleAOB的正负
        // AB连线与X的夹角的tan值 - OB与x轴的夹角的tan值

        //利用tan判断角的正负
        // AB连线与X的夹角的tan值 - OB与x轴的夹角的tan值
        val direction: Float = (a.y - b.y) / (a.x - b.x) - (o.y - b.y) / (o.x - b.x)

        return if (direction == 0f) {
            if (向量OA乘向量OB >= 0) {
                0f
            } else {
                180f
            }
        } else {
            if (direction > 0) {
                -angleAOB
            } else {
                angleAOB
            }
        }
    }

}