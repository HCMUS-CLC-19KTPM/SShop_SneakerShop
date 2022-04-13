package com.example.sshop_sneakershop.Order.models

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class OrderModel {
    private val db = Firebase.firestore
    private val userId = Firebase.auth.currentUser?.uid

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

    suspend fun getOrderById(id: String): Order {
        val order: Order?
        try {
            order = db.collection("order").document(id).get().await().toObject(Order::class.java)
        } catch (e: Exception) {
            throw e
        }

        return order ?: Order()
    }
}