package com.kotlin.hc.two

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.kotlin.hc.R
import com.kotlin.hc.dp2px
import android.graphics.Shader
import android.graphics.Color.parseColor
import android.graphics.LinearGradient
import android.graphics.Color.parseColor
import android.graphics.RadialGradient
import android.graphics.Color.parseColor
import android.graphics.SweepGradient
import android.graphics.BitmapShader
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.ComposeShader


class CustomView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val mPaint = Paint()

    private var mDefaultWidth: Int = dp2px(300)
    private var mDefaultHeight: Int = dp2px(300)


    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        setLayerType(LAYER_TYPE_SOFTWARE, null)
//        mPaint.setShadowLayer(5f, 5f, 5f, Color.BLACK)
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
        canvas.drawColor(Color.TRANSPARENT)
        canvas.save()
        canvas.translate(mDefaultWidth / 2f, mDefaultHeight / 2f)
        val radius = Math.min(mDefaultWidth / 2f, mDefaultHeight / 2f) - 2
//        val shader = LinearGradient(0f, 0f, radius, radius, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP)
//        val shader = SweepGradient(0f, 0f, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"))

//        val options = BitmapFactory.Options()
//        options.inJustDecodeBounds = true
//        options.inSampleSize = Math.min(bitmap.height, bitmap.width)

//        mPaint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
//        canvas.drawCircle(0f, 0f, radius, mPaint)
//        val rectF = RectF(-radius, -radius, radius, radius)
//        canvas.drawArc(rectF, -180f, 120f, false, mPaint)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_gender_male)
        val blurMaskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.SOLID)
        mPaint.maskFilter = blurMaskFilter
        canvas.drawBitmap(bitmap, -bitmap.height / 2f, -bitmap.width / 2f, mPaint)
        canvas.restore()
    }
}