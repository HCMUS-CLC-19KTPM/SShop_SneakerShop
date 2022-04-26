package com.example.sshop_sneakershop.Order.views

import com.example.sshop_sneakershop.Order.models.Order

interface OrderClickListener {
    fun onClick(order: Order)
}