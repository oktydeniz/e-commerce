package com.example.e_commerce_platform.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.e_commerce_platform.model.Product
import com.example.e_commerce_platform.util.NetworkState
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class ProductViewModel(application: Application):BaseViewModel(application) {
    val error =MutableLiveData<Boolean>()
    private lateinit var database: DatabaseReference

    fun setData(product: Product) {
        if (NetworkState.isNetworkAvailable(getApplication())) {
            updateData(product)
                error.value = false
        }
        error.value = true
    }

    private fun updateData(product: Product) {
        launch {
            database = product.productId?.let {
                FirebaseDatabase.getInstance().getReference("uploads/products").child(
                    it
                )
            }!!

            database.setValue(product)
        }


    }

}