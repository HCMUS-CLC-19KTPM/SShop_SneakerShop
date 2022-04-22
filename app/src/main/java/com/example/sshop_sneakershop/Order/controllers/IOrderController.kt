package com.example.sshop_sneakershop.Order.controllers

import com.example.sshop_sneakershop.Order.models.Order

interface IOrderController {
    fun onGetAllOrders()
    fun onGetOrderById(id: String)
    fun onCreateOrder(order: Order)
}