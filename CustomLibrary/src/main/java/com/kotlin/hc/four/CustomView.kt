package com.kotlin.hc.four

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.kotlin.hc.R

class CustomView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mDefaultWidth: Int = 400
    private var mDefaultHeight: Int = 300
    private val mPaint = Paint()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mDefaultWidth = resolveSize(mDefaultWidth, widthMeasureSpec)
        mDefaultHeight = resolveSize(mDefaultHeight, heightMeasureSpec)
        setMeasuredDimension(mDefaultWidth, mDefaultHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.GRAY)
        canvas.save()
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.bg_id_3)
        val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mPaint.shader = shader
        val radius = Math.min(mDefaultWidth / 2f, mDefaultHeight / 2f)
        canvas.translate(radius, radius)
        canvas.drawCircle(radius, radius, radius, mPaint)
        canvas.restore()


        val rectF = RectF()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        event.x
        return super.onTouchEvent(event)
    }

    private fun clipPath(canvas: Canvas) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.bg_id_3)
        canvas.save()
        canvas.translate(mDefaultWidth / 2f, mDefaultHeight / 2f)
        val path = Path()
        path.addCircle(0f, 0f, 100f, Path.Direction.CW)
        canvas.clipPath(path)
        canvas.drawBitmap(bitmap, -mDefaultWidth / 2f, -mDefaultHeight / 2f, mPaint)
        canvas.restore()
    }

    private fun clipRect(canvas: Canvas) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.bg_id_3)
        canvas.save()
        canvas.translate(mDefaultWidth / 2f, mDefaultHeight / 2f)
        val rectF = RectF(-100f, -100f, 200f, 100f)
        canvas.clipRect(rectF)
        canvas.drawBitmap(bitmap, -mDefaultWidth / 2f, -mDefaultHeight / 2f, mPaint)
        canvas.restore()

    }

}