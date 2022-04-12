package com.example.sshop_sneakershop.Cart.controllers

import com.example.sshop_sneakershop.Cart.models.Cart
import com.example.sshop_sneakershop.Cart.models.CartModel

class cartController {
    private val cartModel = CartModel()

    suspend fun getCartByUser(userId: String): Cart {
        return cartModel.getCartByUser(userId)
    }
}