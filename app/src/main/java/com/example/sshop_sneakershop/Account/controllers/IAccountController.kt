package com.example.sshop_sneakershop.Account.controllers

import com.example.sshop_sneakershop.Account.models.Account

interface IAccountController {
    suspend fun getUser(email: String): Account
    suspend fun updateUser(account: Account): Account
}