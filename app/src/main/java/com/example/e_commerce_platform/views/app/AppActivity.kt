package com.example.e_commerce_platform.views.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_commerce_platform.R

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        supportActionBar?.hide()
    }

}

