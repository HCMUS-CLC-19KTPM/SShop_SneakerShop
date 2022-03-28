package com.example.sshop_sneakershop.Auth.Controller

interface IAuthController {
    fun onLogin(email: String, password: String)
    fun onLoginWithGoogle()
    fun onRegister(email: String, password: String)
    fun onForgotPassword(email: String)
    fun onLogout()
}