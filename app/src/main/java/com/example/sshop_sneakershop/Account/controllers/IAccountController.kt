package com.example.sshop_sneakershop.Account.controllers

import android.net.Uri
import com.example.sshop_sneakershop.Account.models.Account

interface IAccountController {
    suspend fun getUser(email: String): Account?

    fun onGetUser()
    fun onCheckIsBanned()
    fun onUpdateAvatar(uri: Uri)
    fun onUpdateUserInfo(account: Account)
    fun onUpdateUserPayment(account: Account)
}