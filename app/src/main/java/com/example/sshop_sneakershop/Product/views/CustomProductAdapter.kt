package com.example.sshop_sneakershop.Product.views

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ProductListItemBinding
import com.example.sshop_sneakershop.databinding.RelatedProductListItemBinding
import com.squareup.picasso.Picasso

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