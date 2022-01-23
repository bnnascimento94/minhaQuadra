package com.example.minhaquadra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        timer()
    }


    private fun timer(){
        Handler().postDelayed(Runnable {
              startActivity(Intent(this,LoginActivity::class.java))
        }, TimeUnit.SECONDS.toMillis(5))
    }
}