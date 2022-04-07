package com.example.sshop_sneakershop.Account.controllers

import com.example.sshop_sneakershop.Account.Account
import com.example.sshop_sneakershop.Account.AccountModel

class AccountController : IAccountController {

    private val accountModel = AccountModel()

    override suspend fun getUser(email: String): Account {
        return accountModel.getUser(email)
    }
}