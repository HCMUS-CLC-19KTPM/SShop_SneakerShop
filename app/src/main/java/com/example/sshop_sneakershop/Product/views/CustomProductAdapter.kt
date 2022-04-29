package com.example.sshop_sneakershop.Product.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.databinding.ProductListItemBinding

class CustomProductAdapter(
    private val products: ArrayList<Product>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<CustomCardViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomCardViewHolder
    {
        val from = LayoutInflater.from(parent.context)
        val binding = ProductListItemBinding.inflate(from, parent, false)
        return CustomCardViewHolder(binding, clickListener)
    }
    override fun onBindViewHolder(holder: CustomCardViewHolder, position: Int) {
        holder.bindItem(products[position])
    }
    override fun getItemCount(): Int = products.size

}