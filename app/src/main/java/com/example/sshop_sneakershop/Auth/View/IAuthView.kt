package com.example.sshop_sneakershop.Auth.View

interface IAuthView {
    fun onLoginSuccess(message: String)
    fun onLoginFailed(message: String)
}