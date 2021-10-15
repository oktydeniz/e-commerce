package com.example.e_commerce_platform.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.e_commerce_platform.R
import com.example.e_commerce_platform.model.Product
import com.example.e_commerce_platform.util.NetworkState
import com.example.e_commerce_platform.util.Util
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class NewProductViewModel(application: Application):BaseViewModel(application) {
    var error = MutableLiveData<Boolean>()
    private lateinit var database: DatabaseReference

    fun setData(product: Product) {
        if (NetworkState.isNetworkAvailable(getApplication())) {
            addNewData(product)
            error.value = false
        }
        error.value = true
    }

    private fun addNewData(product: Product) {
        launch {
            product.productId?.let {
               database = FirebaseDatabase.getInstance().reference
                database.child("uploads/products").child(it).setValue(product)
            }
        }


    }
}