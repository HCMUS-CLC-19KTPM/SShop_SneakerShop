package com.example.sshop_sneakershop.Order.controllers

import com.example.sshop_sneakershop.Product.models.Product
import java.util.*

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
}