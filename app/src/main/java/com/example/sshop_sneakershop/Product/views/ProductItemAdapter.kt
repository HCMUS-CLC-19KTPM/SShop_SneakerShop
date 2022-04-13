package com.example.sshop_sneakershop.Product.views

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.models.ProductInCart
import com.example.sshop_sneakershop.R
import com.squareup.picasso.Picasso

class ProductItemAdapter(private val products: ArrayList<ProductInCart>) :
    RecyclerView.Adapter<ProductItemAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = listItemView.findViewById(R.id.product_textview_name) as TextView
        val priceTextView = listItemView.findViewById(R.id.product_textview_price) as TextView
        val quantityTextView = listItemView.findViewById(R.id.product_textview_quantity) as TextView
        val descriptionTextView =
            listItemView.findViewById(R.id.product_textview_description) as TextView
        val imageView = listItemView.findViewById(R.id.product_image_1) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val productView = inflater.inflate(R.layout.product_list_item, parent, false)
        return ViewHolder(productView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nameTextView.text = product.name
        holder.priceTextView.text = product.price.toString()
        holder.quantityTextView.text = product.quantity.toString()
        holder.descriptionTextView.text = "product.description"

        if (TextUtils.isEmpty(product.image)) {
            holder.imageView.setImageResource(R.drawable.shoe)
        } else {
            Picasso.get().load(product.image).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}