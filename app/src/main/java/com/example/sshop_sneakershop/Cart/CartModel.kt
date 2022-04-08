package com.example.sshop_sneakershop.Cart

import com.example.sshop_sneakershop.Product.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CartModel {
    private var db = Firebase.firestore
    val products = ArrayList<Product>()

    suspend fun getCartByUser(userId: String, callback: (ArrayList<Product>) -> Unit) {
        db.collection("cart").whereEqualTo("userId", userId).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val product = document.toObject(Product::class.java)
                    products.add(product)
                }
                callback(products)
            }
    }
}