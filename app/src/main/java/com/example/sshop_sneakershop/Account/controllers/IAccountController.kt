package com.example.sshop_sneakershop.Account.controllers

import com.example.sshop_sneakershop.Account.Account

interface IAccountController {
    suspend fun getUser(email: String): Account
}