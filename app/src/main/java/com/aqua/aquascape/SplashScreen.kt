package com.aqua.aquascape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import com.aqua.aquascape.Activity.HomeActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(com.aqua.aquascape.R.layout.activity_splashscreen)


        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }, 5000L)
    }
}


