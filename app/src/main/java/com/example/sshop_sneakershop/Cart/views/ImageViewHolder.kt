package com.example.sshop_sneakershop.Cart.views


import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Cart.Cart
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.CartListItemBinding
import com.example.sshop_sneakershop.databinding.RelatedProductListItemBinding
import com.squareup.picasso.Picasso


class ImageViewHolder (
    private val ImageCellBinding: CartListItemBinding,
    private val clickListener: CartClickListener
) : RecyclerView.ViewHolder(ImageCellBinding.root) {
    fun bindItem(product: Cart) {

        if (TextUtils.isEmpty(product.image)) ImageCellBinding.productImage.setImageResource(R.drawable.shoe) else Picasso.get()
            .load(product.image).into(ImageCellBinding.productImage)

        ImageCellBinding.itemTitle.text =
            if (product.name!!.length > 20) "${product.name!!.substring(13)}..." else product.name

        val price = "$" + product.price.toString()
        ImageCellBinding.itemPrice.text = price

        val size = product.size.toString()
        ImageCellBinding.itemSize.text=size

        ImageCellBinding.quantity.setText(product.quantity.toString())

        //ONLY CLICK ON THE IMAGE WILL LINK TO PRODUCT DETAIL
        ImageCellBinding.productImage.setOnClickListener {
            clickListener.onClick(product)
        }

        //IMPLEMENTS LATER
        ImageCellBinding.minus.setOnClickListener{
            val quantity :Int = Integer.valueOf(ImageCellBinding.quantity.text.toString()) - 1
           // if (quantity == 0) Delete item
            ImageCellBinding.quantity.setText(quantity.toString())
        }
        ImageCellBinding.plus.setOnClickListener{
            val quantity = Integer.valueOf(ImageCellBinding.quantity.text.toString()) + 1
            //Cancel if quantity > Product stock
            ImageCellBinding.quantity.setText(quantity.toString())
        }
    }
}