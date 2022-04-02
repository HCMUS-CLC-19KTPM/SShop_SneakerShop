package com.example.sshop_sneakershop.Auth

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthService {
    private val auth = Firebase.auth

    suspend fun signIn(email: String, password: String): String {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Log.d("AuthService", "signInWithEmail:success")

            return "Login success"
        } catch (e: Exception) {
            Log.w("AuthService", "signInWithEmail:failure", e)
        }

        return "Login failed. Invalid email or password"
    }

    suspend fun signInWithGoogle(credential: AuthCredential): String {
        try {
            auth.signInWithCredential(credential).await()
            Log.d("AuthService", "signInWithCredential:success")

            return "Login Success"
        } catch (e: Exception) {
            Log.w(ContentValues.TAG, "signInWithCredential:failure", e)
        }

        return "Login Failed"
    }

    suspend fun signUp(email: String, password: String, confirmPassword: String): String {
        try {
            if (password != confirmPassword) {
                Log.d("AuthService", "signUp:failure")
                return "Password and confirm password do not match"
            }
            auth.createUserWithEmailAndPassword(email, password).await()
            Log.d("AuthService", "createUserWithEmail:success")

            return "Sign up success"
        } catch (e: Exception) {
            Log.w("AuthService", "createUserWithEmail:failure", e)
        }

        return "Sign up failed. Invalid email or password"
    }

    fun signOut() {
        auth.signOut()
    }
}