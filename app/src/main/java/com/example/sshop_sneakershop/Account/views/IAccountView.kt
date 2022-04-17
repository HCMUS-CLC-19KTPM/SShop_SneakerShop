package com.example.sshop_sneakershop.Account.views

import com.example.sshop_sneakershop.Account.models.Account

interface IAccountView {
    // Get
    fun onGetUserSuccess(account: Account)
    fun onGetUserFail(message: String)

    // Update
    fun onUpdateUserInfoSuccess(account: Account)
    fun onUpdateUserInfoFail(message: String)

    fun onUpdateUserPaymentSuccess(account: Account)
    fun onUpdateUserPaymentFail(message: String)
}