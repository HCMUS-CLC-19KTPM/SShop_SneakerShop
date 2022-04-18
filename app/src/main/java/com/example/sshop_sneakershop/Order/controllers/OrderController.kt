package com.example.sshop_sneakershop.Order.controllers

import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Order.models.OrderModel
import com.example.sshop_sneakershop.Order.views.IOrderView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrderController(private val activity: IOrderView? = null): IOrderController {
    private val orderModel = OrderModel()

    override fun onCreateOrder(order: Order) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                activity?.onCreateOrderSuccess(orderModel.createOrder(order))
            } catch (e: Exception) {
                activity?.onCreateOrderFailed("Error: ${e.message}")
            }
        }
    }

    suspend fun getAllOrder() = orderModel.getAllOrders()
    suspend fun getOrderById(id: String) = orderModel.getOrderById(id)
}