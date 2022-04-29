package com.example.sshop_sneakershop.Auth

import android.content.ContentValues
import android.util.Log
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Account.models.AccountModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthService {
    private val auth = Firebase.auth
    private val accountModel = AccountModel()

    @Throws(Exception::class)
    suspend fun signIn(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).await()

            if (!auth.currentUser!!.isEmailVerified) {
                throw Exception("Account is not verified")
            }
        } catch (e: Exception) {
            Log.w("AuthService", "signInWithEmail:failure", e)
            throw e
        }
    }

    @Throws(Exception::class)
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
            throw e
        }
    }

    @Throws(Exception::class)
    suspend fun signUp(email: String, password: String, confirmPassword: String) {
        try {
            if (password != confirmPassword) {
                Log.d("AuthService", "signUp:failure")
                throw Exception("Password and confirm password do not match")
            }
            auth.createUserWithEmailAndPassword(email, password).await()

            val user = auth.currentUser
            user!!.sendEmailVerification().await()

            val newAccount = Account(email)
            newAccount.id = auth.currentUser!!.uid
            accountModel.insertUser(newAccount)

            Log.d("AuthService", "createUserWithEmail:success")
        } catch (e: Exception) {
            Log.w("AuthService", "createUserWithEmail:failure", e)
            throw e
        }
    }

    @Throws(Exception::class)
    suspend fun sendResetPasswordEmail(email: String) {
        try {
            auth.sendPasswordResetEmail(email).await()
            Log.d("AuthService", "sendPasswordResetEmail:success")
        } catch (e: Exception) {
            Log.w("AuthService", "sendPasswordResetEmail:failure", e)
            throw e
        }
    }

    @Throws(Exception::class)
    suspend fun updatePassword(password: String) {
        try {
            auth.currentUser!!.updatePassword(password).await()
            Log.d("AuthService", "updatePassword:success")
        } catch (e: Exception) {
            Log.w("AuthService", "updatePassword:failure", e)
            throw e
        }
    }

    @Throws(Exception::class)
    fun signOut() {
        try {
            auth.signOut()
        } catch (e: Exception) {
            Log.w("AuthService", "signOut:failure", e)
            throw e
        }
    }
}