package com.example.e_commerce_platform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.e_commerce_platform.views.app.AppActivity
import com.example.e_commerce_platform.views.auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        startApp()
    }

    private fun startApp(){

        object: CountDownTimer(1000,1000){
            override fun onTick(p0: Long) {

            }
            override fun onFinish() {
                val user = FirebaseAuth.getInstance().currentUser
                val intent : Intent = if (user !=null){
                    Intent(this@MainActivity,AppActivity::class.java)
                }else{
                    Intent(this@MainActivity,AuthActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }.start()
    }

}