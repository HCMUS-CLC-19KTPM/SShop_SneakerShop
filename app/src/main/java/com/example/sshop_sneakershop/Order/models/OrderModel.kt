package com.example.sshop_sneakershop.Order.models

import com.example.sshop_sneakershop.Product.models.Product
import com.google.firebase.auth.ktx.auth
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
            // TODO: Add order to database and get document's id
            val id = db.collection("order").add(order).await().id
            db.collection("order").document(id).update("id", id)
            order.id = id

            // TODO: Empty product list in cart when order is created
//            db.collection("cart").whereEqualTo("userId", userId).get().await().forEach {
//                it.reference.update("productList", ArrayList<Product>()).await()
//            }

        } catch (e: Exception) {
            throw e
        }

        return order
    }
}