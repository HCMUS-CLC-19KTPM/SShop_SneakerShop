package com.example.sshop_sneakershop.Order.controllers

import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Order.models.OrderModel
import com.example.sshop_sneakershop.Order.views.IOrderDetailActivity
import com.example.sshop_sneakershop.Order.views.IOrderListActivity
import com.example.sshop_sneakershop.Product.models.Product
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

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
    override fun onCreateOrder(
        name: String,
        phone: String,
        address: String,
        cart: ArrayList<Product>,
        totalCost: Double,
        userId: String
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val localDate = LocalDate.now().plusDays(7)
                val endDate = Date.from(localDate.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant())

                val newOrder = orderModel.createOrder(
                    Order(
                        "",
                        name,
                        phone,
                        address,
                        cart,
                        "On Delivery",
                        Date(),
                        Date(),
                        endDate,
                        0.0,
                        totalCost,
                        userId
                    )
                )

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