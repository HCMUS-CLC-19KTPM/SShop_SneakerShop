package com.example.sshop_sneakershop.Product.models

import android.content.ContentValues.TAG
import android.util.Log
import com.example.sshop_sneakershop.Review.models.Review
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.math.RoundingMode
import java.text.DecimalFormat

class ProductModel {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

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
     * Get viewed products
     */
    suspend fun getViewedProducts(limit: Long = 10_000): ArrayList<Product> {
        val products = ArrayList<Product>()

        try {
            db.collection("viewed_product")
                .limit(limit)
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
    /**
     * add review
     */
    suspend fun addReview(productId: String, userId: String, review: Review): Boolean {
        try {
           // get current product
            val product = getProductById(productId)
            var newRating: Double = review.rate
            if (product.rating != 0.0){
                val df = DecimalFormat("#.#")
                df.roundingMode = RoundingMode.DOWN
                newRating = (review.rate + product.rating) / 2
                newRating = df.format(newRating).toDouble()
            }
            // update product rating
            db.collection("product")
                .document(productId)
                .update("rating", newRating)
                .await()
            // update number of review in account
            db.collection("account").whereEqualTo("id", userId).get().await()
                .documents.forEach {
                    it.reference.update("numOfReview", FieldValue.increment(1)).await()
                }
            // add review to product review list
            db.collection("product")
                .document(productId)
                .update("reviews", FieldValue.arrayUnion(review))
                .await()
        } catch (e: Exception) {
            Log.d(TAG, "Error getting documents: ", e)
            return false
        }
        return true
    }

    /**
     * Add product to viewed product list
     */
    @Throws(Exception::class)
    suspend fun addViewedProduct(product: Product) {
        try {
            val ref = db.collection("viewed_product")
                .document(auth.currentUser!!.uid)

            if (ref.get().await().exists()) {
                ref.update("products", FieldValue.arrayUnion(product)).await()
            } else {
                val products = ArrayList<Product>()
                products.add(product)
                ref.set(mapOf("products" to products)).await()
            }
        } catch (e: Exception) {
            throw e
        }
    }
}