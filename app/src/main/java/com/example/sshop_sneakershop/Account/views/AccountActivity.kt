package com.example.sshop_sneakershop.Account.views

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Auth.views.AuthActivity
import com.example.sshop_sneakershop.Cart.CartActivity
import com.example.sshop_sneakershop.Order.views.OrderListActivity
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityUserBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountActivity : AppCompatActivity() {
    private val accountController: AccountController = AccountController()
    private val auth = Firebase.auth

    private lateinit var editInfoBtn: Button
    private lateinit var editPaymentBtn: Button


    private lateinit var avatarImageView: CircularImageView
    private lateinit var usernameTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var genderTextView: TextView
    private lateinit var birthdayTextView: TextView

    private lateinit var logoutBtn: Button
    private lateinit var binding: ActivityUserBinding

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create account's profile info
        createAccountsProfile()

        // Create payment methods info
        createPaymentMethods()

        binding.profileButtonShowCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        binding.profileButtonShowOrder.setOnClickListener {
            startActivity(Intent(this, OrderListActivity::class.java))
        }

        //Back to home
        binding.profileToolbar.setNavigationOnClickListener { finish() }

        logoutBtn = findViewById(R.id.profile_button_logout)
        logoutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

    }

    private fun createAccountsProfile() {

        avatarImageView = findViewById(R.id.profile_image_avatar)
        usernameTextView = findViewById(R.id.profile_text_username)
        nameTextView = findViewById(R.id.profile_text_valueName)
        emailTextView = findViewById(R.id.profile_text_valueEmail)
        addressTextView = findViewById(R.id.profile_text_valueAddress)
        phoneTextView = findViewById(R.id.profile_text_valuePhone)
        genderTextView = findViewById(R.id.profile_text_valueGender)
        birthdayTextView = findViewById(R.id.profile_text_valueDob)

        editInfoBtn = findViewById(R.id.profile_button_editInfo)
        editInfoBtn.visibility = View.GONE

        val infoView = findViewById<GridLayout>(R.id.gridLayout)
        infoView.visibility = View.GONE

        val showInfoBtn = findViewById<Button>(R.id.profile_button_showAccDetail)
        showInfoBtn.setOnClickListener {
            if (infoView.visibility == View.GONE) {
                infoView.visibility = View.VISIBLE
                editInfoBtn.visibility = View.VISIBLE
            } else {
                infoView.visibility = View.GONE
                editInfoBtn.visibility = View.GONE
            }
        }

        editInfoBtn.setOnClickListener {
            val intent = Intent(this, AccountEditActivity::class.java)
            startActivity(intent)
        }

        GlobalScope.launch(Dispatchers.Main) {
            val account = accountController.getUser(auth.currentUser!!.email!!)

            if (!TextUtils.isEmpty(account.avatar)) Picasso.get().load(account.avatar)
                .into(avatarImageView)
            usernameTextView.text = account.email
            nameTextView.text = account.fullName
            emailTextView.text = account.email
            addressTextView.text = account.address
            phoneTextView.text = account.phone
            genderTextView.text = account.gender
            birthdayTextView.text = account.dob
        }
    }

    private fun createPaymentMethods() {
        editPaymentBtn = findViewById(R.id.profile_button_editPayment)
        editPaymentBtn.visibility = View.GONE

        val paymentRcView = findViewById<RecyclerView>(R.id.recyclerView)
        paymentRcView.visibility = View.GONE

        GlobalScope.launch(Dispatchers.Main) {
            val account = accountController.getUser(auth.currentUser!!.email!!)
            val paymentList = account.payments
            if(paymentList!==null) {
                val paymentAdapter = PaymentItemAdapter(paymentList)
                paymentRcView.adapter = paymentAdapter
                paymentRcView.layoutManager = LinearLayoutManager(this@AccountActivity)
            }
        }

        val showPaymentBtn = findViewById<Button>(R.id.profile_button_showPayment)
        showPaymentBtn.setOnClickListener {
            if(paymentRcView.visibility == View.GONE) {
                paymentRcView.visibility = View.VISIBLE
                editPaymentBtn.visibility = View.VISIBLE
            } else {
                paymentRcView.visibility = View.GONE
                editPaymentBtn.visibility = View.GONE
            }
        }

        editPaymentBtn.setOnClickListener {
            val intent = Intent(this, EditPaymentActivity::class.java)
            startActivity(intent)
        }
    }
}