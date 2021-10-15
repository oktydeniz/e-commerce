package com.example.e_commerce_platform.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_platform.databinding.ProductItemBinding
import com.example.e_commerce_platform.model.Product
import com.example.e_commerce_platform.util.RecyclerViewClickInterface

class ProductsAdapter(private val list:ArrayList<Product>,private val clickInterface: RecyclerViewClickInterface):
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    class ViewHolder(var v: ProductItemBinding, var clickInterface: RecyclerViewClickInterface) : RecyclerView.ViewHolder(v.root){
        fun bind(p:Product){
            v.product = p
            v.executePendingBindings()
            v.productItemView.setOnClickListener {
                p.productId?.let { clickInterface.onItemClick(p) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater)
        return ViewHolder(binding,clickInterface)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}