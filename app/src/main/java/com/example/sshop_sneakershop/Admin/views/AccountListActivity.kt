package com.example.sshop_sneakershop.Admin.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.Account
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.checkout.AccountAdapter


class AccountListActivity : AppCompatActivity() {
    private lateinit var accounts: ArrayList<Account>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_account_list)

        val accountRecyclerView = findViewById<RecyclerView>(R.id.admin_account_list_recycler_view)

        val account = Account(
            "1234nsnnnd32312",
            "Nguyễn Văn A",
            "nguyenvana123@gmail.com",
            "dsaj@#mdsa",
            true,
            )
        accounts = listOf(
            account,
            account,
            account,
            account,
            account,
            account,
            account,
            account
        ).toCollection(ArrayList())

        val adapter = AccountAdapter(accounts)

        accountRecyclerView.adapter = adapter

        accountRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)
    }
}