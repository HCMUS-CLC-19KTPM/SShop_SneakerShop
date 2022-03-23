package com.example.sshop_sneakershop.homepage

import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.databinding.HomeProductBinding


class CardViewHolder(
    private val cardCellBinding: HomeProductBinding,
    private val clickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindItem(item: Item)
    {
        cardCellBinding.image.setImageResource(item.cover)
        cardCellBinding.name.text = item.name
        val price = "$"+item.price.toString()
        cardCellBinding.price.text = price

        cardCellBinding.cardView.setOnClickListener{
            clickListener.onClick(item)
        }
    }
}