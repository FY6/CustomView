package com.kotlin.hc

import android.util.TypedValue
import android.view.View

//一些工具方法
fun View.dp2px(dp: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
            resources.displayMetrics).toInt()
}

//一些工具方法
fun View.sp2px(dp: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp.toFloat(),
            resources.displayMetrics).toInt()
}