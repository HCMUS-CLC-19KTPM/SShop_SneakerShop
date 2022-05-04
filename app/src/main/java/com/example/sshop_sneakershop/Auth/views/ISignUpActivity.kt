package com.example.sshop_sneakershop.Auth.views

interface ISignUpActivity {
    fun onSignUpSuccess(message: String? = null)
    fun onSignUpFailed(message: String)
}