package com.kotlin.hc.one

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.kotlin.hc.dp2px
import org.jetbrains.anko.px2dip
import org.jetbrains.anko.px2sp

class CustomView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint()
    private val mTextPaint = TextPaint()
    private var mTextNumber: String = "00000步"
    private var text = "我是文字，hahahhah"


    init {
        mPaint.style = Paint.Style.STROKE//设置绘制模式
        mPaint.strokeWidth = dp2px(5).toFloat()//设置线条宽度
        mPaint.color = Color.GREEN//设置颜色
        mPaint.isAntiAlias = true//设置抗锯齿开关

        /*
            setShadowLayer只有文字绘制阴影支持硬件加速，其它都不支持硬件加速
         */
        setLayerType(LAYER_TYPE_SOFTWARE, null) //关闭硬件加速
        mPaint.setShadowLayer(10f, 2f, 2f, Color.BLACK) // 不能与setMaskFilter同时使用
//        val blurMaskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL)
//        mPaint.maskFilter = blurMaskFilter
    }

    var mDefaultWidth: Int = 400
    var mDefaultHeight: Int = 300

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mDefaultWidth = resolveSize(mDefaultWidth, widthMeasureSpec)
        mDefaultHeight = resolveSize(mDefaultHeight, heightMeasureSpec)
        setMeasuredDimension(mDefaultWidth, mDefaultHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.GRAY)

        canvas.save()//及时保存canvas之前的配置
        canvas.translate(mDefaultWidth / 2f, mDefaultHeight / 2f)//将坐标移动至view的中心，方便绘制
        val radius = Math.min(mDefaultWidth / 2f, mDefaultHeight / 2f) - mPaint.strokeWidth
        canvas.drawCircle(0f, 0f, radius, mPaint)
        canvas.restore()//恢复save之前的配置

        mTextPaint.isAntiAlias = true//抗锯齿
        mTextPaint.textSize = 25f
        mTextPaint.color = Color.RED
        val textWidth = mTextPaint.measureText(text)//测量文本的宽度
        val textHeight = -mTextPaint.ascent() + mTextPaint.descent()//得到文本的高度
        canvas.save()
        canvas.translate(mDefaultWidth / 2f, mDefaultHeight / 2f)
        canvas.drawText(text, 0, text.length, -textWidth / 2, textHeight / 2, mTextPaint)
        canvas.restore()

        canvas.save()
        mTextPaint.textSize = 30f
        mTextPaint.color = Color.WHITE
        //通过Rect获取文本宽度和高度
        val rect = Rect()
        mTextPaint.getTextBounds(mTextNumber, 0, mTextNumber.length, rect)
        val textNumWidth = rect.width().toFloat()
        val textNumHeight = rect.height().toFloat()
        canvas.translate(mDefaultWidth / 2f, mDefaultHeight / 2f)
        canvas.drawText(mTextNumber, 0, mTextNumber.length, -textNumWidth / 2, (textNumHeight / 2) - (textHeight + 10), mTextPaint)
        canvas.restore()

    }

    fun updateNumText(text: String) {
        mTextNumber = "${text}步"
        postInvalidate()
    }

    fun updateText(text: String) {
        this.text = "${text}步"
        postInvalidate()
    }

}