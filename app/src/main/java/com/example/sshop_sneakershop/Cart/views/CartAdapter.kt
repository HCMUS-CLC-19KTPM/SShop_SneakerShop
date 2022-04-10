package com.example.sshop_sneakershop.Cart.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Cart.Cart
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.databinding.CartListItemBinding

class CartAdapter(
    private val products: List<Cart>,
    private val clickListener: CartClickListener
) : RecyclerView.Adapter<ImageViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder
    {
        val from = LayoutInflater.from(parent.context)
        val binding = CartListItemBinding.inflate(from, parent, false)
        return ImageViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int)
    {
        holder.bindItem(products[position])
    }

    override fun getItemCount(): Int = products.size
}