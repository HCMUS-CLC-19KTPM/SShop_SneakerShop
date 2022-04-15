package com.example.sshop_sneakershop.Auth.controllers

import com.example.sshop_sneakershop.Auth.AuthService
import com.example.sshop_sneakershop.Auth.views.IAuthView
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthController(private var view: IAuthView) : IAuthController {

    private var authService: AuthService = AuthService()

    /**
     * Sign in with email and password
     * @param email String
     * @param password String
     */
    override fun onSignIn(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = authService.signIn(email, password)

            if (result) {
                view.onLoginSuccess("Login success")
            } else {
                view.onLoginFailed("Login failed. Invalid email or password or account not activated please check your email")
            }
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
            authService.signUp(email, password, confirmPassword)
            authService.signIn(email, password)

            view.onSignUpSuccess()
        }
    }

    /**
     * Forgot password
     */
    override fun onForgotPassword(email: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val isSuccess = authService.sendResetPasswordEmail(email)

            if (isSuccess) {
                view.onForgotPasswordSuccess("Please check your email to reset your password")
            } else {
                view.onForgotPasswordFailed("Email not found")
            }
        }
    }

    /**
     * Sign out
     */
    override fun onSignOut() {
        authService.signOut()
    }
}