package com.example.sshop_sneakershop.Account.controllers

import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Account.models.AccountModel

class AccountController : IAccountController {

    private val accountModel = AccountModel()

    override suspend fun getUser(email: String): Account {
        return accountModel.getUser(email)
    }

    override suspend fun updateUser(account: Account): Account {
        return accountModel.updateUser(account)
    }
}