package com.kotlin.coder

import android.app.IntentService
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mCustomView.setClassTextArray(arrayOf("信用较差", "信用中等", "信用良好", "信用优秀", "信用极好"))


        mAnimatorBtn
                .setOnClickListener {
                    if (mCurrentProgress.text.toString().isNullOrEmpty()) {
                        Toast.makeText(this, "进度不能为空。", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    mCustomView.update(mCurrentProgress.text.toString())
                }
    }
}
