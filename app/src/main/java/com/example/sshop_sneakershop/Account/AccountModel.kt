package com.example.sshop_sneakershop.Account;

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

        return account?: Account()
    }
}
