package com.kotlin.coder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mCustomView.setClassTextArray(arrayOf("信用较差", "信用中等", "信用良好", "信用优秀", "信用极好"))
        Handler().postDelayed({
            mCustomView.update(1000)

        }, 2000)

        Handler().postDelayed({
            mCustomView.update(350)

        }, 5500)

        Handler().postDelayed({
            mCustomView.update(800)

        }, 7500)


        Handler().postDelayed({
            mCustomView.update(600)

        }, 9500)

        Handler().postDelayed({
            mCustomView.update(4500)

        }, 13000)
    }
}
