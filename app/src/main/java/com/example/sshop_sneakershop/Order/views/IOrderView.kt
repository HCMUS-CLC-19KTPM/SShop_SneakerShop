package com.example.sshop_sneakershop.Order.views

import com.example.sshop_sneakershop.Order.models.Order

interface IOrderView {
    fun onCreateOrderSuccess(order: Order)
    fun onCreateOrderFailed(message: String)
}