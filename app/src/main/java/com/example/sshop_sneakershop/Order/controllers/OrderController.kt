package com.example.sshop_sneakershop.Order.controllers

import com.example.sshop_sneakershop.Order.models.OrderModel

class OrderController {
    private val orderModel = OrderModel()

    suspend fun getAllOrder() = orderModel.getAllOrders()

    suspend fun getOrderById(id: String) = orderModel.getOrderById(id)
}