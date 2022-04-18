package com.example.sshop_sneakershop.Cart.models

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CartModel {
    private val db = Firebase.firestore

    @Throws(Exception::class)
    suspend fun getCartByUser(): Cart? {
        var cart: Cart? = null
        val userId = Firebase.auth.currentUser?.uid

        try {
            db.collection("cart").whereEqualTo("userId", userId).get().await().forEach {
                cart = it.toObject(Cart::class.java)
            }

            if (cart == null) {
                cart = Cart(userId = userId!!)

                db.collection("cart").document().set(cart!!).await()
                db.collection("cart").whereEqualTo("userId", userId).get().await().forEach {
                    db.collection("cart").document(it.id).update("id", it.id).await()
                    cart!!.id = it.id
                }
            }
        } catch (e: Exception) {
            throw e
        }

        return cart
    }

    /**
     * Update cart
     */
    @Throws(Exception::class)
    suspend fun updateProductList(cart: Cart) {
        try {
            val cartId = cart.id

            // Update product list in cart
            db.collection("cart").document(cartId).update("productList", cart.productList).await()
            // Update totalCost in cart
            db.collection("cart").document(cartId).update("totalCost", cart.totalCost).await()

        } catch (e: Exception) {
            throw e
        }
    }
}