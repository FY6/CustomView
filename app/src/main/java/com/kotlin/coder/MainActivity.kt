package com.kotlin.coder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
            while (true) {
                mCustomView.updateNumText("${System.currentTimeMillis()}")
                Thread.sleep(2000)
                mCustomView.updateText("距离目标还有99999")
            }
        }
    }
}
