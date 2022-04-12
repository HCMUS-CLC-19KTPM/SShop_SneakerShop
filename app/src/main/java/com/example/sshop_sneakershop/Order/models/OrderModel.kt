package com.example.sshop_sneakershop.Order.models

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class OrderModel {
    private var db = Firebase.firestore

    suspend fun getAllOrders(): ArrayList<Order> {
        val orders = ArrayList<Order>()
        db.collection("order").get().await().forEach {
            val order = it.toObject(Order::class.java)
            orders.add(order)
        }
        return orders
    }
}