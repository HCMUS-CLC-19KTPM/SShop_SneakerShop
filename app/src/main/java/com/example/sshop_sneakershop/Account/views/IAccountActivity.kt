package com.example.sshop_sneakershop.Account.views

import com.example.sshop_sneakershop.Account.models.Account

interface IAccountActivity {
    // Get
    fun onGetUserSuccess(account: Account)
    fun onGetUserFail(message: String)
    fun onCheckIsBannedSuccess(message: String? = null)
    fun onCheckIsBannedFail(message: String? = null)

    fun onUpdateUserPaymentSuccess(account: Account)
    fun onUpdateUserPaymentFail(message: String)
}