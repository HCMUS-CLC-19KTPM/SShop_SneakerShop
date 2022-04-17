package com.example.sshop_sneakershop.Auth.controllers

import com.google.firebase.auth.AuthCredential

interface IAuthController {
    fun onSignIn(email: String, password: String)
    fun onSignInWithGoogle(credential: AuthCredential)
    fun onSignUp(email: String, password: String, confirmPassword: String)
    fun onForgotPassword(email: String)
    fun onChangePassword(oldPassword: String, newPassword: String, confirmPassword: String)
    fun onSignOut()
}