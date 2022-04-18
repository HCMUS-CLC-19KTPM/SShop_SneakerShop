package com.example.sshop_sneakershop.Cart.controllers

import com.example.sshop_sneakershop.Cart.models.Cart
import com.example.sshop_sneakershop.Cart.models.CartModel
import com.example.sshop_sneakershop.Cart.views.ICartView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartController(private val activity: ICartView? = null) : ICartController {
    private val cartModel = CartModel()

    suspend fun getCartByUser(): Cart? {
        return cartModel.getCartByUser()
    }

    /**
     * Get cart using interface
     */
    override fun onGetCart() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val cart = cartModel.getCartByUser()

                withContext(Dispatchers.Main) {
                    if (cart != null) {
                        activity?.onGetCartSuccess(cart)
                    } else {
                        activity?.onGetCartFailed("Something went wrong")
                    }
                }
            } catch (e: Exception) {
                activity?.onGetCartFailed("Error: ${e.message}")
            }
        }
    }

    /**
     * Update product quantity in cart
     */
    suspend fun updateProductList(cart: Cart) {
        cartModel.updateProductList(cart)
    }
}