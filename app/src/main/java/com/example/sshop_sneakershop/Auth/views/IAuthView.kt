package com.example.sshop_sneakershop.Auth.views

interface IAuthView {
    fun onLoginSuccess(message: String)
    fun onLoginFailed(message: String)
    fun onSignUpSuccess()
    fun onSignUpFailed(message: String)
}