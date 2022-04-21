package com.example.sshop_sneakershop.Auth.controllers

import android.util.Log
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
            try {
                authService.signIn(email, password)
                view.onLoginSuccess("Login success")

            } catch (e: Exception) {
                Log.d("AuthController", e.message.toString())
                view.onLoginFailed("${e.message}")
            }
        }
    }

    /**
     * Sign in with credential
     * @param credential AuthCredential
     */
    override fun onSignInWithGoogle(credential: AuthCredential) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = authService.signInWithGoogle(credential)
                view.onLoginSuccess(result)
            } catch (e: Exception) {
                view.onLoginFailed("${e.message}")
            }
        }
    }

    /**
     * Sign up with email and password
     * @param email String
     * @param password String
     */
    override fun onSignUp(email: String, password: String, confirmPassword: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                authService.signUp(email, password, confirmPassword)
                authService.signIn(email, password)

                view.onSignUpSuccess()
            } catch (e: Exception) {
                view.onSignUpFailed("${e.message}")
            }
        }
    }

    /**
     * Forgot password
     */
    override fun onForgotPassword(email: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                authService.sendResetPasswordEmail(email)

                view.onForgotPasswordSuccess("Please check your email to reset your password")
            } catch (e: Exception) {
                view.onForgotPasswordFailed("${e.message}")
            }
        }
    }

    /**
     * Change password
     */
    override fun onChangePassword(password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                authService.updatePassword(password)
                view.onChangePasswordSuccess("Password changed")
            } catch (e: Exception) {
                view.onChangePasswordFailed("${e.message}")
            }
        }
    }

    /**
     * Sign out
     */
    override fun onSignOut() {
        try {
            authService.signOut()
        } catch (e: Exception) {
            Log.d("AuthController", e.message.toString())
        }
    }
}