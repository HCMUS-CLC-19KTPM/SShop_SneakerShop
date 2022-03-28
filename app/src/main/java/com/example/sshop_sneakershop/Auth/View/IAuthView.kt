package com.example.sshop_sneakershop.Auth.View

interface IAuthView {
    abstract fun onLoginSuccess(message: String)
    abstract fun onLoginFailed(message: String)
}