package com.example.sshop_sneakershop.Product.views

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.RelatedProductListItemBinding
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat


class CardViewHolder(
    private val cardCellBinding: RelatedProductListItemBinding,
    private val clickListener: ItemClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root) {
    @SuppressLint("SetTextI18n")
    fun bindItem(product: Product) {

        if (TextUtils.isEmpty(product.image)) cardCellBinding.image.setImageResource(R.drawable.shoe) else Picasso.get()
            .load(product.image).into(cardCellBinding.image)

        cardCellBinding.name.text =
            if (product.name.length >= 8) "${product.name.substring(0, 8)}..." else product.name

        val price = product.price - (product.price * product.discount / 100)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        cardCellBinding.price.text = "$${df.format(price)}"

        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(product)
        }
    }
}