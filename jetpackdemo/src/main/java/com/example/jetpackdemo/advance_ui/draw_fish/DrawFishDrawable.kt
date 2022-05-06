package com.example.jetpackdemo.advance_ui.draw_fish

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.cos
import kotlin.math.sin

/**
 * 知识点
 * 1. 确定点的坐标的方法 calculatePoint
 * 2. 画贝塞尔曲线
 */
class DrawFishDrawable : Drawable() {

    val path = Path()
    val paint = Paint().apply {
        alpha = 125
    }

    val head_radius = 60f//头的半径

    val body_length = 3.2 * head_radius

    var startAngle = 90f
    var fishAngle = startAngle
        set(value) {

            field = value
        }


    /*    fun start() {
            ValueAnimator.ofFloat(-30f, 30f).apply {
                duration = 1000L
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                interpolator = LinearInterpolator()
                addUpdateListener {
                    fishAngle = startAngle + (it.animatedValue as Float)
                }
                start()
            }
        }   */
    var currentValue = 0f

    /**
     * 让鱼尾摆动频率和鱼身不一样
     */
    fun start() {
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 1000L
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            //interpolator = LinearInterpolator()
            addUpdateListener {
                currentValue = it.animatedValue as Float
                invalidateSelf()
            }
            start()
        }
    }

    //肢节上的大圆
    val big_circle_radus = 0.7 * head_radius

    //肢节上的中圆
    val middle_circle_radus = 0.42 * head_radius

    //肢节上的小圆
    val small_circle_radus = 0.168 * head_radius


    //肢节2长度
    val ziJie2Length = 1.302f * head_radius

    //大三角形边长
    val bigTriangleLength = 1 * head_radius

    //小三角形边长
    val smallTriangleLength = 0.7 * head_radius

    /**
     * 初始时，鱼是横着放的
     * 鱼动的时候是围绕着重心动的，重心位置不变
     */
    //重心坐标
    val middlePoint = PointF(4.19f * head_radius, 4.19f * head_radius)

    //鱼头坐标
    lateinit var headCenterPoint: PointF


    //鱼头圆心和右鱼鳍右边的距离
    private val head_fins_length = 0.9f * head_radius

    //鱼鳍长度
    private val fins_length = 1.3f * head_radius

    //鱼身摆动幅度
    private val body_rang = 20f
    private val body_frequency = 1f

    //肢节1摆动幅度
    private val ziJie1_rang=30f
    private val ziJie1_frequency=1.5f

    //肢节2摆动幅度
    private val ziJie2_rang=30f
    private val ziJie2_frequency=1.5f


    //三角形摆动幅度摆动幅度
    private val tail_rang=30f
    private val tail_frequency=2f



    override fun getIntrinsicWidth(): Int {
        return (8.38 * head_radius).toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return (8.38 * head_radius).toInt()
    }


    //鱼鳍宽度

    override fun draw(canvas: Canvas) {

        fishAngle = startAngle + sin(Math.toRadians((body_frequency*currentValue.toDouble()))).toFloat() * body_rang


        //以重心为起点，计算鱼头的圆心坐标
        headCenterPoint = calculatePoint(middlePoint, (body_length / 2).toFloat(), fishAngle)
        canvas.drawCircle(headCenterPoint.x, headCenterPoint.y, head_radius, paint)

        //左鱼鳍
        drawRightFin(headCenterPoint, canvas, true)

        //右鱼鳍
        drawRightFin(headCenterPoint, canvas, false)

        //肢节底部中心,因为肢节底部相对于头部的距离是不变的，所以这里仍然中fishAngle
        val ziJieBottomPoint =
            calculatePoint(
                headCenterPoint,

                body_length.toFloat(),

                fishAngle + 180f
            )

        //肢节1
        val recordPointF = drawZiJie(canvas, ziJieBottomPoint, true)

        //肢节2
        drawZiJie(canvas, recordPointF[0], false)

        //绘制鱼尾的三角形
        drawTail(recordPointF[0], canvas, true)

        drawTail(recordPointF[0], canvas, false)

        //绘制身体
        drawBody(recordPointF, headCenterPoint, canvas)

    }

    private fun drawBody(
        recordPointF: Array<PointF>,
        headCenterPoint: PointF,
        canvas: Canvas
    ) {
        val leftTop = recordPointF[2]
        val rightTop = calculatePoint(headCenterPoint, head_fins_length, fishAngle + 90f)
        val leftBottom = recordPointF[1]
        val rightBottom = calculatePoint(headCenterPoint, head_fins_length, fishAngle - 90f)
        val controlPointFTop = calculatePoint(
            headCenterPoint,
            head_fins_length * 1.8f,
            fishAngle + 130f
        )  //PointF((leftTop.x + rightTop.x) / 2, rightTop.y - 0.5f * head_radius)
        val controlPointFBottom =
            calculatePoint(headCenterPoint, head_fins_length * 1.8f, fishAngle - 130f)
        path.reset()
        path.moveTo(leftTop.x, leftTop.y)
        path.quadTo(controlPointFTop.x, controlPointFTop.y, rightTop.x, rightTop.y)
        path.lineTo(rightBottom.x, rightBottom.y)
        path.quadTo(controlPointFBottom.x, controlPointFBottom.y, leftBottom.x, leftBottom.y)
        canvas.drawPath(path, paint)
    }

    /**
     *
     * 绘制鱼尾的三角形
     *
     */
    private fun drawTail(
        triangleStartPointF: PointF,
        canvas: Canvas,
        isBigTail: Boolean
    ) {

        val angle= startAngle + sin(Math.toRadians((tail_frequency * currentValue.toDouble()))).toFloat() * tail_rang

        val trianglePointF1 =
            calculatePoint(
                triangleStartPointF,
                if (isBigTail) {
                    bigTriangleLength
                } else {
                    smallTriangleLength
                }.toFloat(),
                if (isBigTail) {
                    angle + 150f
                } else {
                    angle + 155f
                }
            )
        val trianglePointF2 =
            calculatePoint(
                triangleStartPointF,
                if (isBigTail) {
                    bigTriangleLength
                } else {
                    smallTriangleLength
                }.toFloat(),
                if (isBigTail) {
                    angle - 150f
                } else {
                    angle - 155f
                }
            )

        path.reset()
        path.moveTo(triangleStartPointF.x, triangleStartPointF.y)
        path.lineTo(trianglePointF1.x, trianglePointF1.y)
        path.lineTo(trianglePointF2.x, trianglePointF2.y)
        canvas.drawPath(path, paint)
    }

    /**
     * 绘制肢节1或者2
     *
     * recordPointF[0] 存放 肢节顶部中心
     * recordPointF[1] 存放 肢节底部右点
     * recordPointF[2] 存放 肢节底部左点
     */
    private fun drawZiJie(
        canvas: Canvas,
        ziJieBottomPoint: PointF,
        isZiJie1: Boolean
    ): Array<PointF> {

        val ziJieAngle =
            if(isZiJie1) {
                startAngle + cos(Math.toRadians((ziJie1_frequency * currentValue.toDouble()))).toFloat() * ziJie1_rang
            }else{
                startAngle + sin(Math.toRadians((ziJie2_frequency* currentValue.toDouble()))).toFloat() * ziJie2_rang
            }

        //底部的圆
        if (isZiJie1) {
            canvas.drawCircle(
                ziJieBottomPoint.x,
                ziJieBottomPoint.y,
                big_circle_radus.toFloat(),
                paint
            )
        }


        //梯形的四个点

        val ziJieBottomLeft =
            calculatePoint(
                ziJieBottomPoint,
                if (isZiJie1) {
                    big_circle_radus.toFloat()
                } else {
                    middle_circle_radus.toFloat()
                },
                fishAngle + 90f
            )
        val ziJieBottomRight =
            calculatePoint(
                ziJieBottomPoint,
                if (isZiJie1) {
                    big_circle_radus.toFloat()
                } else {
                    middle_circle_radus.toFloat()
                },
                fishAngle - 90f
            )

        //肢节顶部中心
        val ziJieTopCenterPoint = calculatePoint(
            ziJieBottomPoint,
            if (isZiJie1) {
                (big_circle_radus + middle_circle_radus).toFloat()
            } else {
                ziJie2Length
            },
            ziJieAngle + 180f
        )

        val ziJieTopLeft =
            calculatePoint(
                ziJieTopCenterPoint,
                if (isZiJie1) {
                    middle_circle_radus.toFloat()
                } else {
                    small_circle_radus.toFloat()
                },
                ziJieAngle + 90f
            )


        val ziJieTopRight =
            calculatePoint(
                ziJieTopCenterPoint,
                if (isZiJie1) {
                    middle_circle_radus.toFloat()
                } else {
                    small_circle_radus.toFloat()
                },
                ziJieAngle - 90f
            )


        canvas.drawCircle(
            ziJieTopCenterPoint.x,
            ziJieTopCenterPoint.y,
            if (isZiJie1) {
                middle_circle_radus.toFloat()
            } else {
                small_circle_radus.toFloat()
            },
            paint
        )

        path.reset()
        path.moveTo(ziJieBottomLeft.x, ziJieBottomLeft.y)
        path.lineTo(ziJieBottomRight.x, ziJieBottomRight.y)
        path.lineTo(ziJieTopRight.x, ziJieTopRight.y)
        path.lineTo(ziJieTopLeft.x, ziJieTopLeft.y)
        canvas.drawPath(path, paint)

        return arrayOf(ziJieTopCenterPoint, ziJieBottomRight, ziJieBottomLeft)
    }

    private fun drawRightFin(
        headCenterPoint: PointF,
        canvas: Canvas,
        isRight: Boolean//是否是右鱼鳍
    ) {
        //鱼鳍,以鱼头圆心为起点，计算右鱼鳍的右边的坐标
        //length和angle都是估算的一个值
        val rightFindStartPoint = calculatePoint(
            headCenterPoint, head_fins_length, if (isRight) {
                fishAngle - 110
            } else {
                fishAngle + 110
            }
        )

        //以rightFindStartPoint为起点，计算右鱼鳍的左边的坐标
        val rightFindEndPoint = calculatePoint(rightFindStartPoint, fins_length, fishAngle - 180)

        //以rightFindStartPoint为起点，计算使用二阶贝塞尔绘制鱼鳍时的控制点
        //length和angle都是估算的
        val rightFindControlPoint =
            calculatePoint(
                rightFindStartPoint, fins_length * 1.8f, if (isRight) {
                    fishAngle - 150
                } else {
                    fishAngle + 150
                }
            )

        path.reset()
        path.moveTo(rightFindStartPoint.x, rightFindStartPoint.y)
        path.quadTo(
            rightFindControlPoint.x,
            rightFindControlPoint.y,
            rightFindEndPoint.x,
            rightFindEndPoint.y
        )
        canvas.drawPath(path, paint)
    }

    /**
     * 确定目标点的坐标的公式
     * @startPoint  起点的坐标
     * @length  目标点和起点的距离
     * @angle   起点与终点连接额直线与水平方向的夹角
     */
    fun calculatePoint(startPoint: PointF, length: Float, angle: Float): PointF {
        //cos/sin的参数是弧度，所以要先将角度转为弧度
        val dx = cos(Math.toRadians(angle.toDouble())) * length
        //因为三角函数的坐标系的y轴和android的坐标系的y轴是相反的，所以sin这里减去一个180度，或者值取负也行
        val dy = sin(Math.toRadians(angle.toDouble() - 180)) * length
        return PointF(startPoint.x + dx.toFloat(), startPoint.y + dy.toFloat())
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }
}