package com.example.sshop_sneakershop.Account.controllers

import com.example.sshop_sneakershop.Account.models.Account

interface IPaymentController {
    fun onUpdatePayment(account: Account)
}