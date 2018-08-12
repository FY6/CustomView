package com.kotlin.hc

import android.util.TypedValue
import android.view.View
import java.util.regex.Pattern

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

/*
    判断是否是数字
 */
fun String.isDouble(): Boolean {
    val pattern = Pattern.compile("^[-\\+]?[.\\d]*$")
    return pattern.matcher(this).matches()
}