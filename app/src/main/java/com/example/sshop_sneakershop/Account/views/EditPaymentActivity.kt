package com.example.sshop_sneakershop.Account.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditPaymentActivity : AppCompatActivity() {

    private val accountController: AccountController = AccountController()
    private val auth = Firebase.auth

    private lateinit var addPaymentBtn: FloatingActionButton
    private lateinit var listPaymentRcView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_payment)

        addPaymentBtn = findViewById(R.id.editPayment_addCart_button)
        listPaymentRcView = findViewById(R.id.editPayment_atmList_RC)

        GlobalScope.launch(Dispatchers.Main) {
            val account = accountController.getUser(auth.currentUser!!.email!!)
            val paymentList = account.payments
            if(paymentList!==null) {
                val paymentAdapter = PaymentDetailItemAdapter(paymentList)
                listPaymentRcView.adapter = paymentAdapter
                listPaymentRcView.layoutManager = LinearLayoutManager(this@EditPaymentActivity)
            }
        }

        addPaymentBtn.setOnClickListener {
            val intent = Intent (this, AddPaymentActivity::class.java)
            startActivity(intent)
        }

    }
}