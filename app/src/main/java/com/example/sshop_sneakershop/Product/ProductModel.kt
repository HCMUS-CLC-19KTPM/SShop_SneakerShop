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
                    val product = it.toObject(Product::class.java)
                    product!!.id = it.id
                    products.add(product)
                }
        } catch (e: Exception) {
            Log.d(TAG, "Error getting documents: ", e)
        }

        return products
    }

    /**
     * Get product by id
     */
    suspend fun getProductById(id: String): Product {
        var product = Product()
        try {
            db.collection("product")
                .document(id)
                .get()
                .await()
                .toObject(Product::class.java)
                ?.let {
                    product = it
                }
        } catch (e: Exception) {
            Log.d(TAG, "Error getting documents: ", e)
        }

        return product
    }
}