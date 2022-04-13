package com.example.sshop_sneakershop.Cart.views

import com.example.sshop_sneakershop.Product.models.ProductInCart

interface CartClickListener {
    fun onClick(cart: ProductInCart)
}
