package com.example.sshop_sneakershop.Cart.models

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CartModel {
    private val db = Firebase.firestore

    suspend fun getCartByUser(): Cart? {
        var cart: Cart? = null
        val userId = Firebase.auth.currentUser?.uid

        try {
            db.collection("cart").whereEqualTo("userId", userId).get().await().forEach {
                cart = it.toObject(Cart::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return cart
    }

    /**
     * Update cart
     */
    suspend fun updateProductList(cart: Cart) {
        try {
            val cartId = cart.id
            db.collection("cart").document(cartId).update("productList", cart.productList).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}