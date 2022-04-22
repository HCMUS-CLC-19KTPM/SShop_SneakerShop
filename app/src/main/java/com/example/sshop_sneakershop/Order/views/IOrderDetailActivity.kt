package com.example.sshop_sneakershop.Order.views

import com.example.sshop_sneakershop.Order.models.Order

interface IOrderDetailActivity {
    fun onGetOrderByIdSuccess(order: Order)
    fun onGetOrderByIdFailed(message: String)
}