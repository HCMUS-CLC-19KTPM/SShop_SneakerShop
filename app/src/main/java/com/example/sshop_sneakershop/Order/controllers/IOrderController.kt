package com.example.sshop_sneakershop.Order.controllers

import com.example.sshop_sneakershop.Order.models.Order

interface IOrderController {
    fun onCreateOrder(order: Order)
}