package com.example.sshop_sneakershop.Product.models

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProductModel {
    private var db = Firebase.firestore

    /**
     * Get all products
     */
    suspend fun getAllProducts(limit: Long = 10_000): ArrayList<Product> {
        val products = ArrayList<Product>()

        try {
            db.collection("product")
                .limit(limit)
                .orderBy("releaseDate", Query.Direction.DESCENDING)
                .get()
                .await()
                .documents
                .forEach {
                    val product = it.toObject(Product::class.java)
                    products.add(product!!)
                }
        } catch (e: Exception) {
            Log.w(TAG, "Error getting documents.", e)
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

    /**
     * Get product by category
     */
    suspend fun getProductsByCategory(category: String): ArrayList<Product> {
        val products = ArrayList<Product>()

        try {
            db.collection("product")
                .whereEqualTo("category", category)
                .orderBy("releaseDate", Query.Direction.DESCENDING)
                .get()
                .await()
                .documents
                .forEach {
                    val product = it.toObject(Product::class.java)!!
                    products.add(product)
                }
        } catch (e: Exception) {
            Log.w(TAG, "Error getting documents.", e)
        }

        return products
    }
}