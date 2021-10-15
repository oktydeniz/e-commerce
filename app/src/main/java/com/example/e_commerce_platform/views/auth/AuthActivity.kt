package com.example.e_commerce_platform.views.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_platform.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()
    }

}