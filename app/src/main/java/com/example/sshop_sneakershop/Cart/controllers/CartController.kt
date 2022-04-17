package com.example.sshop_sneakershop.Cart.controllers

import com.example.sshop_sneakershop.Cart.models.Cart
import com.example.sshop_sneakershop.Cart.models.CartModel
import com.example.sshop_sneakershop.Product.models.Product

class CartController {
    private val cartModel = CartModel()

    suspend fun getCartByUser(): Cart? {
        return cartModel.getCartByUser()
    }

    /**
     * Update product quantity in cart
     */
    suspend fun updateProductList(cart: Cart) {
        cartModel.updateProductList(cart)
    }
}