package com.example.e_commerce_platform.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:loadImage")
fun loadImage(v: ImageView, url: String?) {
    Glide.with(v.context).load(Util.imgUrl).into(v)
}