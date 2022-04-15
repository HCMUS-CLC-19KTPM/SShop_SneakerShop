package com.example.sshop_sneakershop.Cart.controllers

import com.example.sshop_sneakershop.Cart.models.Cart
import com.example.sshop_sneakershop.Cart.models.CartModel

class CartController {
    private val cartModel = CartModel()

    suspend fun getCartByUser(): Cart? {
        return cartModel.getCartByUser()
    }
}