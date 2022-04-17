package com.example.sshop_sneakershop.Account.controllers

import com.example.sshop_sneakershop.Account.models.Account

interface IAccountController {
    fun onGetUser(email: String)
    fun onUpdateUserInfo(account: Account)
    fun onUpdateUserPayment(account: Account)
}