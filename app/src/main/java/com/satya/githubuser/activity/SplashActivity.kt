package com.satya.githubuser.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.satya.githubuser.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler(mainLooper).postDelayed({
            val moveIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(moveIntent)
            finish()
        },3000 )
    }
}