package com.example.sshop_sneakershop.Auth

import android.content.ContentValues
import android.util.Log
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Account.models.AccountModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthService {
    private val auth = Firebase.auth
    private val accountModel = AccountModel()

    suspend fun signIn(email: String, password: String): Boolean {
        try {
            auth.signInWithEmailAndPassword(email, password).await()

            if (!auth.currentUser!!.isEmailVerified) {
                return false
            }
            Log.d("AuthService", "signInWithEmail:success")
            return true
        } catch (e: Exception) {
            Log.w("AuthService", "signInWithEmail:failure", e)
        }

        return false
    }

    suspend fun signInWithGoogle(credential: AuthCredential): String {
        try {
            auth.signInWithCredential(credential).await()

            val email: String? = auth.currentUser!!.email
            if (accountModel.getUser(email!!) == null) {
                accountModel.insertUser(Account(email))
            }

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

            val user = auth.currentUser
            user!!.sendEmailVerification().await()

            val newAccount = Account(email)
            newAccount.id = auth.currentUser!!.uid
            accountModel.insertUser(newAccount)

            Log.d("AuthService", "createUserWithEmail:success")

            return "Sign up success"
        } catch (e: Exception) {
            Log.w("AuthService", "createUserWithEmail:failure", e)
        }

        return "Sign up failed. Invalid email or password"
    }

    suspend fun sendResetPasswordEmail(email: String): Boolean {
        try {
            auth.sendPasswordResetEmail(email).await()
            Log.d("AuthService", "sendPasswordResetEmail:success")

            return true
        } catch (e: Exception) {
            Log.w("AuthService", "sendPasswordResetEmail:failure", e)
        }

        return false
    }

    suspend fun updatePassword(password: String): Boolean {
        try {
            auth.currentUser!!.updatePassword(password).await()
            Log.d("AuthService", "updatePassword:success")
            return true
        } catch (e: Exception) {
            Log.w("AuthService", "updatePassword:failure", e)
        }
        return false
    }

    fun signOut() {
        auth.signOut()
    }
}