package com.example.e_commerce_platform.util

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

class Util {

    companion object {

        const val imgUrl = "https://www.arraymedical.com/wp-content/uploads/2018/12/product-image-placeholder.jpg"
        fun inputError(input:EditText,message:String){
            if(input.text.toString().isEmpty()) input.error = message
        }
        fun toastMakerS(c:Context,message:String){
            Toast.makeText(c,message,Toast.LENGTH_SHORT).show()
        }
        fun toastMakerL(c:Context,message:String){
            Toast.makeText(c,message,Toast.LENGTH_LONG).show()
        }
         inline fun <reified T: Enum<T>> getNames() = enumValues<T>().map { it.name }
    }
}