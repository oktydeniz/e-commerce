package com.example.e_commerce_platform.model

import com.example.e_commerce_platform.util.Util
import java.io.Serializable

data class Product(val productId :String?,
                   val name:String?,
                   val price:String?,
                   val image:String?,
                   val category:String?,
                    val description:String?,
                    val added_date:String?):Serializable{
    constructor() : this("", "", "", "","" ,"","")
}
