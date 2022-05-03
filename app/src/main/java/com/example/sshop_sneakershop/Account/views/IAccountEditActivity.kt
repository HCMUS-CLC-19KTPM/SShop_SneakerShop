package com.example.sshop_sneakershop.Account.views

import com.example.sshop_sneakershop.Account.models.Account

interface IAccountEditActivity {
    // Update
    fun onUpdateUserInfoSuccess(account: Account)
    fun onUpdateUserInfoFailed(message: String)
    fun onUpdateAvatarSuccess(imageURL: String)
    fun onUpdateAvatarFail(message: String)

}