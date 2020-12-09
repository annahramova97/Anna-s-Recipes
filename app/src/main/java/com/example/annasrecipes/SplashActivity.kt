package com.example.annasrecipes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.annasrecipes.R
import java.util.*

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000 //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        Handler().postDelayed({
//
//            startActivity(Intent(this,
//                ScrollingActivity::class.java))
//
//            finish()
//        }, SPLASH_TIME_OUT)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
        }, SPLASH_TIME_OUT)


    }
}