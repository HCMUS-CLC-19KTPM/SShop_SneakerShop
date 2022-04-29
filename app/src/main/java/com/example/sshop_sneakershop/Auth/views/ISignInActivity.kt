package com.example.sshop_sneakershop.Auth.views

interface ISignInActivity {
    fun onLoginSuccess(message: String)
    fun onLoginFailed(message: String)
}