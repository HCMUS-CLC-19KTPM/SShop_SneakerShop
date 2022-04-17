package com.example.sshop_sneakershop.Account.models

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AccountModel {
    private var db = Firebase.firestore

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
