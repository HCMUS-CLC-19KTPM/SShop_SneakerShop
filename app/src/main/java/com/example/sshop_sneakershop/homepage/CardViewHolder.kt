package com.example.sshop_sneakershop.homepage

import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.databinding.HomeProductBinding


class CardViewHolder(
    private val cardCellBinding: HomeProductBinding,
    private val clickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindItem(product: Product)
    {
        cardCellBinding.image.setImageResource(product.image!!.toInt())
        cardCellBinding.name.text = product.name
        val price = "$"+product.price.toString()
        cardCellBinding.price.text = price

        cardCellBinding.cardView.setOnClickListener{
            clickListener.onClick(product)
        }
    }
}