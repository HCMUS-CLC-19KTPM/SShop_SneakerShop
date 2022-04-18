package com.example.sshop_sneakershop.Product.views

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.RelatedProductListItemBinding
import com.squareup.picasso.Picasso


class CardViewHolder(
    private val cardCellBinding: RelatedProductListItemBinding,
    private val clickListener: ItemClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindItem(product: Product) {

        if (TextUtils.isEmpty(product.image)) cardCellBinding.image.setImageResource(R.drawable.shoe) else Picasso.get()
            .load(product.image).into(cardCellBinding.image)

        cardCellBinding.name.text =
            if (product.name.length >= 20) "${product.name.substring(20)}..." else product.name

        val price = "$" + product.price.toString()
        cardCellBinding.price.text = price

        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(product)
        }
    }
}