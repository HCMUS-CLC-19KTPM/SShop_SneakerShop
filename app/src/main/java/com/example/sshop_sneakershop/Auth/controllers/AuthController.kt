package com.example.sshop_sneakershop.Auth.controllers

import android.util.Log
import com.example.sshop_sneakershop.Auth.AuthService
import com.example.sshop_sneakershop.Auth.views.ISignInActivity
import com.example.sshop_sneakershop.Auth.views.IChangePasswordActivity
import com.example.sshop_sneakershop.Auth.views.IForgotPasswordActivity
import com.example.sshop_sneakershop.Auth.views.ISignUpActivity
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthController : IAuthController {
    private var signInActivity: ISignInActivity? = null
    private var changePasswordActivity: IChangePasswordActivity? = null
    private var forgotPasswordActivity: IForgotPasswordActivity? = null
    private var signUpActivity: ISignUpActivity? = null

    private var authService: AuthService = AuthService()

    constructor(signInActivity: ISignInActivity? = null) {
        this.signInActivity = signInActivity
    }

    constructor(changePasswordActivity: IChangePasswordActivity? = null) {
        this.changePasswordActivity = changePasswordActivity
    }

    constructor(forgotPasswordActivity: IForgotPasswordActivity? = null) {
        this.forgotPasswordActivity = forgotPasswordActivity
    }

    constructor(signUpActivity: ISignUpActivity? = null) {
        this.signUpActivity = signUpActivity
    }

    /**
     * Sign in with email and password
     * @param email String
     * @param password String
     */
    override fun onSignIn(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                authService.signIn(email, password)
                signInActivity?.onLoginSuccess("Login success")

            } catch (e: Exception) {
                Log.d("AuthController", e.message.toString())
                signInActivity?.onLoginFailed("${e.message}")
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
                signInActivity?.onLoginSuccess(result)
            } catch (e: Exception) {
                signInActivity?.onLoginFailed("${e.message}")
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

                signUpActivity?.onSignUpSuccess()
            } catch (e: Exception) {
                signUpActivity?.onSignUpFailed("${e.message}")
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

                forgotPasswordActivity?.onForgotPasswordSuccess("Please check your email to reset your password")
            } catch (e: Exception) {
                forgotPasswordActivity?.onForgotPasswordFailed("${e.message}")
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
                changePasswordActivity?.onChangePasswordSuccess("Password changed")
            } catch (e: Exception) {
                changePasswordActivity?.onChangePasswordFailed("${e.message}")
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