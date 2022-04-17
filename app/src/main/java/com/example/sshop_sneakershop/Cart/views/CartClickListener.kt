package com.example.sshop_sneakershop.Cart.views

import com.example.sshop_sneakershop.Product.models.Product

interface CartClickListener {
    fun onClick(cart: Product)
    fun onChangeQuantity(position: Int, quantity: Int)
}
