package com.example.sshop_sneakershop.Product.views

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ProductListItemBinding
import com.example.sshop_sneakershop.databinding.RelatedProductListItemBinding
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat

class CustomCardViewHolder(
    private val cardCellBinding: ProductListItemBinding,
    private val clickListener: ItemClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root) {
    @SuppressLint("SetTextI18n")
    fun bindItem(product: Product) {

        if (TextUtils.isEmpty(product.image)) cardCellBinding.productImage1.setImageResource(R.drawable.shoe) else Picasso.get()
            .load(product.image).into(cardCellBinding.productImage1)

        cardCellBinding.productTextviewName.text =
            if (product.name.length >= 8) "${product.name.substring(0, 8)}..." else product.name

        cardCellBinding.productTextviewPrice.text = "$" + product.price * product.quantity
        cardCellBinding.productTextviewQuantity.text = product.quantity.toString()
        cardCellBinding.productTextviewDescription.text =
            if (product.description!!.length >= 20) "${
                product.description!!.substring(
                    0,
                    80
                )
            }..." else product.description
        cardCellBinding.card.setOnClickListener {
            clickListener.onClick(product)
        }
    }
}