package com.example.sshop_sneakershop.Account.views

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Account.controllers.IAccountController
import com.example.sshop_sneakershop.Account.controllers.IPaymentController
import com.example.sshop_sneakershop.Account.controllers.PaymentController
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Account.models.Payment
import com.example.sshop_sneakershop.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PaymentEditActivity : AppCompatActivity(), IPaymentEditActivity, PaymentClickListener {

    private val accountController: AccountController = AccountController()
    private val auth = Firebase.auth

    private lateinit var addPaymentBtn: FloatingActionButton
    private lateinit var listPaymentRcView: RecyclerView
    private lateinit var toolbar: MaterialToolbar

    private lateinit var paymentController: PaymentController
    private var account: Account? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_payment)

        paymentController = PaymentController(this)
        addPaymentBtn = findViewById(R.id.editPayment_addCart_button)
        listPaymentRcView = findViewById(R.id.editPayment_atmList_RC)
        toolbar = findViewById(R.id.editPayment_toolbar)

        GlobalScope.launch(Dispatchers.Main) {
            account = accountController.getUser(auth.currentUser!!.email!!)
            if (account != null) {
                val paymentList = account!!.payments
                if (paymentList !== null) {
                    val paymentAdapter = PaymentDetailItemAdapter(paymentList, this@PaymentEditActivity)
                    listPaymentRcView.adapter = paymentAdapter
                    listPaymentRcView.layoutManager = LinearLayoutManager(this@PaymentEditActivity)
                }
            }
        }

        addPaymentBtn.setOnClickListener {
            val intent = Intent (this, AddPaymentActivity::class.java)
            startActivity(intent)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.Main) {
            account = accountController.getUser(auth.currentUser!!.email!!)
            if (account != null) {
                val paymentList = account!!.payments
                if (paymentList !== null) {
                    val paymentAdapter = PaymentDetailItemAdapter(paymentList,this@PaymentEditActivity)
                    listPaymentRcView.adapter = paymentAdapter
                    listPaymentRcView.layoutManager = LinearLayoutManager(this@PaymentEditActivity)

                }
            }
        }
    }

    /**
     * On remove a card
     */
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onRemoveCard(position: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            account = accountController.getUser(auth.currentUser!!.email!!)
            if (account != null) {
                val paymentList = account!!.payments
                if (paymentList !== null) {
                    paymentList.removeAt(position)
                    paymentController.onUpdatePayment(account!!)
                }
            }
        }
    }

    override fun onUpdatePaymentSuccess(account: Account) {
        this.account = account
        val paymentList = this.account!!.payments
        if (paymentList !== null) {
            val paymentAdapter = PaymentDetailItemAdapter(paymentList, this@PaymentEditActivity)
            listPaymentRcView.adapter = paymentAdapter
            listPaymentRcView.layoutManager = LinearLayoutManager(this@PaymentEditActivity)
        }
    }

    override fun onUpdatePaymentFail(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}