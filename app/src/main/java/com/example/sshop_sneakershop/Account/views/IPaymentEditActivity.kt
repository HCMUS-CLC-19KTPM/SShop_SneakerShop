package com.example.sshop_sneakershop.Account.views

import com.example.sshop_sneakershop.Account.models.Account

interface IPaymentEditActivity {
    fun onUpdatePaymentSuccess(account: Account)
    fun onUpdatePaymentFail(message: String)
}