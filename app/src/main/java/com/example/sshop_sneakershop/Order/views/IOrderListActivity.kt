package com.example.sshop_sneakershop.Order.views

import com.example.sshop_sneakershop.Order.models.Order

interface IOrderListActivity {
    fun onGetAllOrdersSuccess(orders: ArrayList<Order>)
    fun onGetAllOrdersFailed(message: String)

    fun onCreateOrderSuccess(order: Order)
    fun onCreateOrderFailed(message: String)
}