package com.example.sshop_sneakershop.Cart.models

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CartModel {
    private var db = Firebase.firestore

    suspend fun getCartByUser(userId: String): Cart {
        var cart: Cart? = null

        try {
            db.collection("cart").whereEqualTo("userId", userId).get().await().forEach {
                cart = it.toObject(Cart::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return cart ?: Cart()
    }
}