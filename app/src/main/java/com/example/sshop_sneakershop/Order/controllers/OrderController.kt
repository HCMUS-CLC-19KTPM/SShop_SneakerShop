package com.example.sshop_sneakershop.Order.controllers

import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Order.models.OrderModel
import com.example.sshop_sneakershop.Order.views.IOrderListActivity
import kotlinx.coroutines.*

class OrderController(private val activity: IOrderListActivity? = null): IOrderController {
    private val orderModel = OrderModel()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onGetAllOrders() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val orders = orderModel.getAllOrders()

                withContext(Dispatchers.Main) {
                    activity?.onGetAllOrdersSuccess(orders)
                }
            } catch (e: Exception) {
                activity?.onGetAllOrdersFailed("Error: ${e.message}")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateOrder(order: Order) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val newOrder = orderModel.createOrder(order)

                withContext(Dispatchers.Main) {
                    activity?.onCreateOrderSuccess(newOrder)
                }
            } catch (e: Exception) {
                activity?.onCreateOrderFailed("Error: ${e.message}")
            }
        }
    }

    suspend fun getAllOrder() = orderModel.getAllOrders()
    suspend fun getOrderById(id: String) = orderModel.getOrderById(id)

    suspend fun createOrder(order: Order) = orderModel.createOrder(order)
}