package com.kotlin.hc.one

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ArcView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mDefaultWidth: Int = 400
    private var mDefaultHeight: Int = 300
    private val mPaint = Paint()

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.RED
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mDefaultWidth = resolveSize(mDefaultWidth, widthMeasureSpec)
        mDefaultHeight = resolveSize(mDefaultHeight, heightMeasureSpec)
        setMeasuredDimension(mDefaultWidth, mDefaultHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.parseColor("#125a6d"))

        canvas.save()
        canvas.translate(mDefaultWidth / 2f, mDefaultHeight / 2f)
        val radius = Math.min(mDefaultWidth / 2f, mDefaultHeight / 2f)

        val rectF = RectF(-radius + 5, -radius + 5, radius, radius)
        canvas.drawArc(rectF, -180f, 120f, true, mPaint)
        canvas.restore()


        canvas.save()
        mPaint.color = Color.YELLOW
        canvas.translate(mDefaultWidth / 2f + 5, mDefaultHeight / 2f + 5)
        val radiu2 = Math.min(mDefaultWidth / 2f, mDefaultHeight / 2f)

        val rectF2 = RectF(-radiu2 + 5, -radiu2 + 5, radiu2 + 5, radiu2 + 5)
        canvas.drawArc(rectF2, -60f, 60f, true, mPaint)

        canvas.save()
        mPaint.color = Color.parseColor("#7d3d98")
        val radiu3 = Math.min(mDefaultWidth / 2f, mDefaultHeight / 2f)
        val rectF3 = RectF(-radiu3 + 5, -radiu3 + 5, radiu3 + 5, radiu3 + 5)
        canvas.drawArc(rectF3, 0f, 180f, true, mPaint)
        canvas.restore()



    }
}