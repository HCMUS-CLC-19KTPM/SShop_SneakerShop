package com.example.sshop_sneakershop.Auth.Controller

import android.content.ContentValues.TAG
import android.util.Log
import com.example.sshop_sneakershop.Auth.View.IAuthView
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthController(private var view: IAuthView) : IAuthController {
    private val auth = Firebase.auth

    /**
     * Sign in with email and password
     * @param email String
     * @param password String
     */
    override fun onSignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "signInWithEmail:success")
                view.onLoginSuccess("Login Success")
            } else {
                Log.w(TAG, "signInWithEmail:failure", it.exception)
                view.onLoginFailed("Login Failed")
            }
        }
    }

    /**
     * Sign in with credential
     * @param credential AuthCredential
     */
    override fun onSignInWithGoogle(credential: AuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "signInWithCredential:success")
                view.onLoginSuccess("Login Success")
            } else {
                Log.w(TAG, "signInWithCredential:failure", it.exception)
                view.onLoginFailed("Login Failed")
            }
        }
    }

    /**
     * Sign up with email and password
     * @param email String
     * @param password String
     */
    override fun onSignUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
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
        auth.signOut()
    }
}