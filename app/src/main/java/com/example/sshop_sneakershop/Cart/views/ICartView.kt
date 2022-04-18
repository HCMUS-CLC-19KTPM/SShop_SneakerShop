package com.example.sshop_sneakershop.Cart.views

import com.example.sshop_sneakershop.Cart.models.Cart

interface ICartView {
    fun onGetCartSuccess(cart: Cart)
    fun onGetCartFailed(message: String)
}