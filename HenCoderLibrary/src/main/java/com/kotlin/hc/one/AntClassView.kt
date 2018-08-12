package com.kotlin.hc.one

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import com.kotlin.hc.R
import com.kotlin.hc.dp2px
import com.kotlin.hc.sp2px

/**
 * 芝麻信用
 *
 * Math.toDegrees 弧度转化为角度 Math.toRadians 角度转化为弧度
 *
 */
class AntClassView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //内部圆弧线宽度
    private var mInnerStrokeWidth = 0f
    //外部圆弧线宽度
    private var mOutStrokeWidth = 0f

    /*
       我们传的是角度，所以后面使用可能需要将其转为弧度
     */
    //默认开始画圆弧的角度
    private var mStartAngle = 0f
    //默认圆弧扫过的度数
    private var mSweepAngle = 0f

    //刻度数量，等分30份
    private val mScacleNumber = 30
    //刻度值
    private var mScaleValue = 0f
    //最大进度
    private var mMaxProgress = 900
    private var mIndicatorAnimator: ObjectAnimator? = null
    //当前进度
    private var currentProgress = 0
    //字体大小
    private var mTextSize = 0f

    private var mClassThred = 0


    private var mClassTextArray: Array<String> = emptyArray()

    fun setClassTextArray(texts: Array<String>) {
        this.mClassTextArray = texts
        mClassThred = mMaxProgress / mClassTextArray.size
    }

    fun getClassTextArray() = mClassTextArray

    fun setCurrentProgress(currentProgress: Int) {
        this.currentProgress = currentProgress
        invalidate()
    }

    fun getCurrentProgress() = currentProgress

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AntClass, 0, 0)
        mInnerStrokeWidth = typedArray.getDimension(R.styleable.AntClass_innercircle_stroke_Width, dp2px(5).toFloat())
        mOutStrokeWidth = typedArray.getDimension(R.styleable.AntClass_outcircle_stroke_Width, dp2px(2).toFloat())
        mStartAngle = typedArray.getFloat(R.styleable.AntClass_start_angle, 160f)
        mSweepAngle = typedArray.getFloat(R.styleable.AntClass_sweep_angle, 220f)
        mMaxProgress = typedArray.getInt(R.styleable.AntClass_max_progress, 900)
        currentProgress = typedArray.getInt(R.styleable.AntClass_current_progress, 450)
        mTextSize = typedArray.getDimension(R.styleable.AntClass_text_size, sp2px(25).toFloat())
        mScaleValue = mSweepAngle / mScacleNumber
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(dp2px(400), widthMeasureSpec)
        val height = resolveSize(dp2px(400), heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.parseColor("#ff00ddff"))
        drawCircle(canvas)
        drawScale(canvas)
        drawIndicator(canvas)
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas) {
        canvas.save()
        canvas.translate(measuredWidth / 2f, measuredHeight / 2f)
        val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = mTextSize
        paint.color = Color.WHITE
        val textClass = getClassText()
        val rect2 = Rect()
        paint.getTextBounds(textClass, 0, textClass.length, rect2)
        canvas.drawText(textClass, 0, textClass.length, -rect2.width() / 2f, rect2.height() / 2f + dp2px(5), paint)

        paint.reset()
        paint.textSize = mTextSize
        paint.color = Color.WHITE
        val radius = Math.min(measuredWidth / 4f, measuredHeight / 4f)
        val str = "$currentProgress"
        val rect = Rect()
        paint.getTextBounds(str, 0, str.length, rect)
        canvas.drawText(str, 0, str.length, -rect.width() / 2f, rect.height() / 2f - radius / 3, paint)

        canvas.restore()
    }

    private fun getClassText(): String {
        for (index in 0 until mClassTextArray.size) {
            if (currentProgress <= (index + 1) * mClassThred) {
                return mClassTextArray[index]
            }
        }
        return mClassTextArray[mClassTextArray.size - 1]
    }

    private fun drawIndicator(canvas: Canvas) {
        canvas.save()
        canvas.translate(measuredWidth / 2f, measuredHeight / 2f)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = mOutStrokeWidth + dp2px(2)

        val intArrayOf = intArrayOf(0xffffffff.toInt(), 0x00ffffff, 0x99ffffff.toInt(), 0xffffffff.toInt())
        paint.shader = SweepGradient(measuredWidth / 2f, measuredHeight / 2f, intArrayOf, null)

        val sweep = currentProgress.toFloat() / mMaxProgress * mSweepAngle
        val radius = Math.min(measuredWidth / 4f, measuredHeight / 4f) + mInnerStrokeWidth + paint.strokeWidth + dp2px(5)
        val rectF = RectF(-radius, -radius, radius, radius)
        canvas.drawArc(rectF, mStartAngle, sweep, false, paint)
        /*
            画小圆点
         */
        paint.reset()
        paint.strokeWidth = mOutStrokeWidth + dp2px(1)
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.maskFilter = BlurMaskFilter(dp2px(2).toFloat(), BlurMaskFilter.Blur.NORMAL)
        //三角函数以弧度作为参数，所以需要转换为弧度
        val y = Math.sin(Math.toRadians((sweep + mStartAngle).toDouble())) * radius
        val x = Math.cos(Math.toRadians((sweep + mStartAngle).toDouble())) * radius
        canvas.drawCircle(x.toFloat(), y.toFloat(), paint.strokeWidth + dp2px(1).toFloat(), paint)
        canvas.restore()
    }

    private fun drawScale(canvas: Canvas) {
        canvas.save()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.parseColor("#D4AF72")
        paint.alpha = 0x70
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dp2px(3).toFloat()
        canvas.translate(measuredWidth / 2f, measuredHeight / 2f)
        val radius = Math.min(measuredWidth / 4f, measuredHeight / 4f)
        canvas.rotate(-270 + mStartAngle)
        for (index in 0..mScacleNumber) {
            canvas.drawLine(0f, -radius + mInnerStrokeWidth / 2, 0f, -radius - mInnerStrokeWidth / 2, paint)
            canvas.rotate(mScaleValue)
        }
        canvas.restore()
    }

    private fun drawCircle(canvas: Canvas) {
        drawInnerCircle(canvas)
        drawOutCircle(canvas)
    }

    private fun drawInnerCircle(canvas: Canvas) {
        canvas.save()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.maskFilter = BlurMaskFilter(dp2px(2).toFloat(), BlurMaskFilter.Blur.NORMAL)
        paint.alpha = 0x70
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = mInnerStrokeWidth
        canvas.translate(measuredWidth / 2f, measuredHeight / 2f)
        val radius = Math.min(measuredWidth / 4f, measuredHeight / 4f)
        val rectF = RectF(-radius, -radius, radius, radius)
        canvas.drawArc(rectF, mStartAngle, mSweepAngle, false, paint)
        canvas.restore()
    }

    private fun drawOutCircle(canvas: Canvas) {
        canvas.save()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.alpha = 0x70
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = mOutStrokeWidth

        canvas.translate(measuredWidth / 2f, measuredHeight / 2f)
        val radius = Math.min(measuredWidth / 4f, measuredHeight / 4f) + mInnerStrokeWidth + paint.strokeWidth + dp2px(5)
        val rectF = RectF(-radius, -radius, radius, radius)
        canvas.drawArc(rectF, mStartAngle, mSweepAngle, false, paint)
        canvas.restore()
    }

    /**
     * 进度更新
     */
    fun update(progress: Int) {
        val tmpCurrentProgress = if (progress >= mMaxProgress) mMaxProgress else progress
        mIndicatorAnimator = ObjectAnimator.ofInt(this, "currentProgress", tmpCurrentProgress)
        mIndicatorAnimator?.apply {
            this.duration = 2000
            this.interpolator = BounceInterpolator()
            this.start()
        }
    }
}