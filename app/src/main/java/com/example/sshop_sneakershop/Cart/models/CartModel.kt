package com.example.sshop_sneakershop.Cart.models

import com.example.sshop_sneakershop.Product.models.Product
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CartModel {
    private val db = Firebase.firestore

    @Throws(Exception::class)
    suspend fun getCart(): Cart? {
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
     * Add to cart
     */
    @Throws(Exception::class)
    suspend fun addToCart(productId: String, size: String) {
        try {
            val cart = getCart()
            val productList = cart!!.productList
            val product = db.collection("product").document(productId).get().await().toObject(
                    Product::class.java
                )

            if (productList.stream().noneMatch { it.id == productId }) {
                product!!.quantity = 1
                product.size = size
                productList.add(product)
            } else {
                // Check size
                productList.stream().filter { it.size == size }.forEach {
                    it.quantity++
                }

                // Add new product if size doesn't exist
                if (productList.stream().noneMatch { it.size == size }) {
                    product!!.quantity = 1
                    product.size = size
                    productList.add(product)
                }
            }

            db.collection("cart").document(cart.id).update("productList", productList).await()
        } catch (e: Exception) {
            throw e
        }
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