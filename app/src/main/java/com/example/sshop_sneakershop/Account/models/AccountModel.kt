package com.example.sshop_sneakershop.Account.models

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AccountModel {
    private var db = Firebase.firestore
    private var storageRef = Firebase.storage.reference

    suspend fun getUser(): Account? {
        var account: Account? = null
        val email: String? = Firebase.auth.currentUser?.email

        try {
            db.collection("account").whereEqualTo("email", email).get().await()
                .documents.forEach {
                    account = it.toObject(Account::class.java)!!
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return account
    }

    suspend fun getUser(email: String): Account? {
        var account: Account? = null

        try {
            db.collection("account").whereEqualTo("email", email).get().await()
                .documents.forEach {
                    account = it.toObject(Account::class.java)!!
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return account
    }

    suspend fun getPayments(id: String): ArrayList<Payment>? {
        var payments: ArrayList<Payment>? = null

        try {
            db.collection("account").whereEqualTo("id", id).get().await()
                .documents.forEach {
                    payments = it.data!!["payments"] as ArrayList<Payment>?
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return payments
    }

    suspend fun insertUser(account: Account) {
        try {
            db.collection("account").document().set(account).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Update
    /**
     * Update user's avatar
     */
    suspend fun uploadImage(imageUri: Uri): String? {
        val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formater.format(now)
        var url = ""
        try {
            storageRef.child("account/${fileName}.jpg").putFile(imageUri).await()
            val uri = storageRef.child("account/${fileName}.jpg").downloadUrl.await()
            url = uri.toString()
            Log.i("image-url", "1: ${url}")
            return url
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Update user's information
     */
    suspend fun updateUserInfo(account: Account): Account? {
        var modifiedAccount: Account? = null

        try {
            db.collection("account").whereEqualTo("id", account.id).get().await()
                .documents.forEach {
                    it.reference.update(account.toMap()).await()
                    modifiedAccount = account
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return modifiedAccount
    }

    /**
     * Update user's payments
     */
    suspend fun updateUserPayment(account: Account): Account? {
        var modifiedAccount: Account? = null

        try {
            db.collection("account").whereEqualTo("id", account.id).get().await()
                .documents.forEach {
                    it.reference.update("payments", account.payments).await()
                    modifiedAccount = account
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return modifiedAccount
    }
}
