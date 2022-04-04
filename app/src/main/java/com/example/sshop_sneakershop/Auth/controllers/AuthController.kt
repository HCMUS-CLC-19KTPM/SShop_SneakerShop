package com.example.sshop_sneakershop.Auth.controllers

import com.example.sshop_sneakershop.Auth.AuthService
import com.example.sshop_sneakershop.Auth.views.IAuthView
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthController(private var view: IAuthView) : IAuthController {

    private lateinit var authService: AuthService

    init {
        authService = AuthService()
    }

    /**
     * Sign in with email and password
     * @param email String
     * @param password String
     */
    override fun onSignIn(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = authService.signIn(email, password)
            view.onLoginSuccess(result)
        }
    }

    /**
     * Sign in with credential
     * @param credential AuthCredential
     */
    override fun onSignInWithGoogle(credential: AuthCredential) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = authService.signInWithGoogle(credential)
            view.onLoginSuccess(result)
        }
    }

    /**
     * Sign up with email and password
     * @param email String
     * @param password String
     */
    override fun onSignUp(email: String, password: String, confirmPassword: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = authService.signUp(email, password, confirmPassword)
            authService.signIn(email, password)

            view.onSignUpSuccess()
        }
    }

    /**
     * Forgot password
     */
    override fun onForgotPassword(email: String) {
        TODO("Not yet implemented")
    }

    /**
     * Sign out
     */
    override fun onSignOut() {
        authService.signOut()
    }
}