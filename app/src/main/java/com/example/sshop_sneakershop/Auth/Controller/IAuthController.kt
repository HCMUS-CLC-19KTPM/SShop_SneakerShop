package com.example.sshop_sneakershop.Auth.Controller

import com.google.firebase.auth.AuthCredential

interface IAuthController {
    fun onSignIn(email: String, password: String)
    fun onSignInWithGoogle(credential: AuthCredential)
    fun onSignUp(email: String, password: String, confirmPassword: String)
    fun onForgotPassword(email: String)
    fun onSignOut()
}