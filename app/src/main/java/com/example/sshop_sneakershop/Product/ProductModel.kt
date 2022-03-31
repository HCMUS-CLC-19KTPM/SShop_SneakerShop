package com.example.sshop_sneakershop.Product

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProductModel {
    private var db = Firebase.firestore
    val products = ArrayList<Product>()

    /**
     * Get all products
     */
    suspend fun getAllProducts(): ArrayList<Product> {
        try {
            db.collection("product")
                .get()
                .await()
                .documents
                .forEach {
                    products.add(
                        Product(
                            it.id,
                            (it.data!!["price"] as Long).toDouble(),
                            it.data!!["name"] as String,
                            it.data!!["image"] as String,
                            it.data!!["description"] as String,
                            (it.data!!["quantity"] as Long).toInt(),
                        )
                    )
                }
        } catch (e: Exception) {
            Log.d(TAG, "Error getting documents: ", e)
        }

        return products
    }
}