package com.example.sshop_sneakershop.Auth.Controller

import android.content.ContentValues.TAG
import android.provider.Settings.Secure.getString
import android.util.Log
import com.example.sshop_sneakershop.Auth.View.IAuthView
import com.example.sshop_sneakershop.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthController(private var view: IAuthView) : IAuthController {
    private val auth = Firebase.auth

    override fun onLogin(email: String, password: String) {
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

    override fun onLoginWithGoogle() {
        TODO("Not yet implemented")
//        BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    // Your server's client ID, not your Android client ID.
//                    .setServerClientId(getString(R.string.your_web_client_id))
//                    // Only show accounts previously used to sign in.
//                    .setFilterByAuthorizedAccounts(true)
//                    .build())
//            .build()
    }

    override fun onRegister(email: String, password: String) {
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

    override fun onForgotPassword(email: String) {
        TODO("Not yet implemented")
    }

    override fun onLogout() {
        TODO("Not yet implemented")
    }
}