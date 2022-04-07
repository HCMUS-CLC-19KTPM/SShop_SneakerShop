package com.example.sshop_sneakershop.Product.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.databinding.RelatedProductListItemBinding


class ProductAdapter(
    private val products: List<Product>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<CardViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder
    {
        val from = LayoutInflater.from(parent.context)
        val binding = RelatedProductListItemBinding.inflate(from, parent, false)
        return CardViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int)
    {
        holder.bindItem(products[position])
    }

    override fun getItemCount(): Int = products.size
}