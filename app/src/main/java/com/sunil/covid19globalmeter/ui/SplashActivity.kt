package com.sunil.covid19globalmeter.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sunil.covid19globalmeter.R
import com.sunil.covid19globalmeter.ui.activity.Covid19DashboardActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, Covid19DashboardActivity::class.java))
            finish()
        }, 3000)
    }
}