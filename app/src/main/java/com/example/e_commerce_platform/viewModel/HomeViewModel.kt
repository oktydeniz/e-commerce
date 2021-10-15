package com.example.e_commerce_platform.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.e_commerce_platform.model.Product
import com.example.e_commerce_platform.util.NetworkState
import com.google.firebase.database.*
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {
     val productMLDModel = MutableLiveData<ArrayList<Product>>()
     var productArrayList = ArrayList<Product>()
    private lateinit var database:DatabaseReference
     val isSuccess = MutableLiveData<Boolean>()
     val isLoading = MutableLiveData<Boolean>()

    fun getData() {
        if (NetworkState.isNetworkAvailable(getApplication())) {
            database = FirebaseDatabase.getInstance().getReference("uploads/products")
            isSuccess.value = true
            isLoading.value = true
            getDataFr()

        } else {
            isSuccess.value = false
            isLoading.value = false
        }
    }
    private fun getDataFr(){
        productArrayList.clear()
        productMLDModel.value = productArrayList
        launch {
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    val iterator = dataSnapshot.children
                    productArrayList.clear()
                    for (c in iterator){
                        val pr = c.getValue(Product::class.java)
                        if (pr != null) {
                            productArrayList.add(pr)
                        }
                    }
                    productMLDModel.value = productArrayList
                    isLoading.value = false
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }

            database.addValueEventListener(postListener)
        }

    }
    fun deleteProduct(p:Product){
        database.child(p.productId.toString()).removeValue()

    }

}