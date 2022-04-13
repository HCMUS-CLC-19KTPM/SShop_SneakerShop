package com.example.sshop_sneakershop.Account.models;

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AccountModel {
    private var db = Firebase.firestore

    suspend fun getUser(email: String): Account {
        var account: Account? = null

        try {
            db.collection("account").whereEqualTo("email", email).get().await()
                .documents.forEach {
                    account = it.toObject(Account::class.java)!!
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return account ?: Account()
    }

    suspend fun insertUser(account: Account) {
        try {
            db.collection("account").document().set(account).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateUser(account: Account): Account {
        var modifiedAccount: Account? = null

        try {
            db.collection("account").document(account.id).set(account).await()

            db.collection("account").document(account.id).get().await()
                .toObject(Account::class.java)?.let {
                    modifiedAccount = it
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return modifiedAccount ?: Account()
    }
}
