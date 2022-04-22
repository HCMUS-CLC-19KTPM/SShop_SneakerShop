package com.example.sshop_sneakershop.Order.models

import com.example.sshop_sneakershop.Product.models.Product
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class OrderModel {
    private val db = Firebase.firestore
    private val userId = Firebase.auth.currentUser?.uid

    @Throws(Exception::class)
    suspend fun getAllOrders(): ArrayList<Order> {
        val orders = ArrayList<Order>()
        try {
            db.collection("order").whereEqualTo("userId", userId).get().await().forEach {
                val order = it.toObject(Order::class.java)
                orders.add(order)
            }
        } catch (e: Exception) {
            throw e
        }

        return orders
    }

    @Throws(Exception::class)
    suspend fun getOrderById(id: String): Order? {
        val order: Order?
        try {
            order = db.collection("order").document(id).get().await().toObject(Order::class.java)
        } catch (e: Exception) {
            throw e
        }

        return order
    }

    @Throws(Exception::class)
    suspend fun createOrder(order: Order): Order {
        try {
            // Add order to database and get document's id
            val id = db.collection("order").add(order).await().id
            db.collection("order").document(id).update("id", id)
            order.id = id

            // Sold product
            var stock: ArrayList<Int>
            for (product in order.cart) {
                // Update stock
                db.collection("product").document(product.id).get().await().let {
                    stock = it.data?.get("stock") as ArrayList<Int>

                    when (product.size) {
                        "US7" -> {
                            if (stock[0] > 0) {
                                stock[0] = stock[0] - product.quantity
                            } else {
                                throw Exception("Out of stock")
                            }
                        }
                        "US8" -> {
                            if (stock[1] > 0) {
                                stock[1] = stock[1] - product.quantity
                            } else {
                                throw Exception("Out of stock")
                            }
                        }
                        "US9" -> {
                            if (stock[2] > 0) {
                                stock[2] = stock[2] - product.quantity
                            } else {
                                throw Exception("Out of stock")
                            }
                        }
                        "US10" -> {
                            if (stock[3] > 0) {
                                stock[3] = stock[3] - product.quantity
                            } else {
                                throw Exception("Out of stock")
                            }
                        }
                        else -> {}
                    }
                }
                db.collection("product").document(product.id).update("stock", stock)

                // Create sold product
                db.collection("sold_product").add(product)
            }

            // Empty product list in cart when order is created
            db.collection("cart").whereEqualTo("userId", userId).get().await().forEach {
                it.reference.update("productList", ArrayList<Product>()).await()
            }
        } catch (e: Exception) {
            throw e
        }

        return order
    }
}