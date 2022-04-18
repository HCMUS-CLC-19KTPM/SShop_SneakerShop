package com.example.sshop_sneakershop.Order.controllers

import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Order.models.OrderModel
import com.example.sshop_sneakershop.Order.views.IOrderView

class OrderController(private val activity: IOrderView? = null): IOrderController {
    private val orderModel = OrderModel()

    override fun onCreateOrder(order: Order) {
        orderModel
    }

    suspend fun getAllOrder() = orderModel.getAllOrders()
    suspend fun getOrderById(id: String) = orderModel.getOrderById(id)
}