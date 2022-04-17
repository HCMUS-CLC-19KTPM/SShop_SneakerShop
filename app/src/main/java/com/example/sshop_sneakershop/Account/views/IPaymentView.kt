package com.example.sshop_sneakershop.Account.views

import com.example.sshop_sneakershop.Account.models.Payment

interface IPaymentView {
    fun onGetPaymentSuccess(payments: ArrayList<Payment>)
    fun onGetPaymentFail(message: String)

    fun onUpdateUserPaymentSuccess(payments: ArrayList<Payment>)
    fun onUpdateUserPaymentFail(message: String)
}