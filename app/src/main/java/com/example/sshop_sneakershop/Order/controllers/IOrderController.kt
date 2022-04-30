package com.example.sshop_sneakershop.Order.controllers

import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Product.models.Product
import java.util.*
import kotlin.collections.ArrayList

interface IOrderController {
    fun onGetAllOrders()
    fun onGetOrderById(id: String)
    fun onCreateOrder(
        name: String,
        phone: String,
        address: String,
        cart: ArrayList<Product>,
        totalCost: Double,
        userId: String
    )

    suspend fun getAllOrder(): ArrayList<Order>
}