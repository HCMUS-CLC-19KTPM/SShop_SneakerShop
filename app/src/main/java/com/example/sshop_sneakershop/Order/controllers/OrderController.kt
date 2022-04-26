package com.example.sshop_sneakershop.Order.controllers

import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Order.models.OrderModel
import com.example.sshop_sneakershop.Order.views.IOrderDetailActivity
import com.example.sshop_sneakershop.Order.views.IOrderListActivity
import kotlinx.coroutines.*

class OrderController(
    private val orderListActivity: IOrderListActivity? = null,
    private val orderDetailActivity: IOrderDetailActivity? = null
) : IOrderController {
    private val orderModel = OrderModel()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onGetAllOrders() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val orders = orderModel.getAllOrders()

                withContext(Dispatchers.Main) {
                    orderListActivity?.onGetAllOrdersSuccess(orders)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    orderListActivity?.onGetAllOrdersFailed("Error: ${e.message}")
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onGetOrderById(id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val order = orderModel.getOrderById(id)

                withContext(Dispatchers.Main) {
                    orderDetailActivity?.onGetOrderByIdSuccess(order)
                }
//                    order?.let { orderDetailActivity?.onGetOrderByIdSuccess(it) }
//                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    orderDetailActivity?.onGetOrderByIdFailed("Error: ${e.message}")
                }
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateOrder(order: Order) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val newOrder = orderModel.createOrder(order)

                withContext(Dispatchers.Main) {
                    orderListActivity?.onCreateOrderSuccess(newOrder)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    orderListActivity?.onCreateOrderFailed("Error: ${e.message}")
                }
            }
        }
    }

    suspend fun getAllOrder() = orderModel.getAllOrders()
    suspend fun getOrderById(id: String) = orderModel.getOrderById(id)

    suspend fun createOrder(order: Order) = orderModel.createOrder(order)
}