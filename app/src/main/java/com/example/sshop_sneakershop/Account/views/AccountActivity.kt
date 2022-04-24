package com.example.sshop_sneakershop.Account.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Account.models.Payment
import com.example.sshop_sneakershop.Auth.views.AuthActivity
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityUserBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class AccountActivity : AppCompatActivity(), IAccountActivity {
    private lateinit var linearInfoLayout: LinearLayout
    private lateinit var linearPaymentLayout: LinearLayout
    private lateinit var paymentRcView: RecyclerView

    private lateinit var avatarImageView: CircularImageView
    private lateinit var usernameTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var genderTextView: TextView
    private lateinit var birthdayTextView: TextView
    private lateinit var editInfoBtn: Button
    private lateinit var editPaymentBtn: Button
    private lateinit var logoutBtn: Button

    private lateinit var binding: ActivityUserBinding

    private lateinit var accountController: AccountController
    private val auth = Firebase.auth

    private val paymentList = ArrayList<Payment>()

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) if not, go to login page
        if (auth.currentUser == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        // TODO: Get latest account info
        accountController.onGetUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountController = AccountController(this)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Back to home
        binding.profileToolbar.setNavigationOnClickListener { finish() }

        logoutBtn = findViewById(R.id.profile_button_logout)
        logoutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        // Create account's profile info
        createAccountProfile()
        // Create payment methods info
        createPaymentMethods()
    }

    /**
     * Create account's profile info
      */
    private fun createAccountProfile() {
        avatarImageView = binding.profileImageAvatar
        usernameTextView = binding.profileTextUsername
        nameTextView = binding.profileTextValueName
        emailTextView = binding.profileTextValueEmail
        addressTextView = binding.profileTextValueAddress
        phoneTextView = binding.profileTextValuePhone
        genderTextView = binding.profileTextValueGender
        birthdayTextView = binding.profileTextValueDob
        editInfoBtn = binding.profileButtonEditInfo

        val infoView = binding.gridLayout
        linearInfoLayout = binding.profileLinearLayoutAccDetail
        linearInfoLayout.setOnClickListener {
            if (infoView.visibility == View.GONE) {
                infoView.visibility = View.VISIBLE
                editInfoBtn.visibility = View.VISIBLE
            } else {
                infoView.visibility = View.GONE
                editInfoBtn.visibility = View.GONE
            }
        }

        val showInfoBtn = binding.profileButtonShowAccDetail
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

        // Get account's info from database
        accountController.onGetUser()
    }

    /**
     * Create payment methods info
     */
    private fun createPaymentMethods() {
        editPaymentBtn = binding.profileButtonEditPayment
        editPaymentBtn.visibility = View.GONE

        paymentRcView = binding.recyclerView
        paymentRcView.apply {
            layoutManager = LinearLayoutManager(this@AccountActivity)
            adapter = PaymentItemAdapter(paymentList)
        }
        paymentRcView.visibility = View.GONE

        linearPaymentLayout = binding.profileLinearLayoutPayment
        linearPaymentLayout.setOnClickListener {
            if (paymentRcView.visibility == View.GONE) {
                paymentRcView.visibility = View.VISIBLE
                editPaymentBtn.visibility = View.VISIBLE
            } else {
                paymentRcView.visibility = View.GONE
                editPaymentBtn.visibility = View.GONE
            }
        }

        val showPaymentBtn = binding.profileButtonShowPayment
        showPaymentBtn.setOnClickListener {
            if (paymentRcView.visibility == View.GONE) {
                paymentRcView.visibility = View.VISIBLE
                editPaymentBtn.visibility = View.VISIBLE
            } else {
                paymentRcView.visibility = View.GONE
                editPaymentBtn.visibility = View.GONE
            }
        }

        editPaymentBtn.setOnClickListener {
            val intent = Intent(this, PaymentEditActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetUserSuccess(account: Account) {
        // User's info
        if (!TextUtils.isEmpty(account.avatar)) Picasso.get().load(account.avatar)
            .into(avatarImageView)
        usernameTextView.text = account.fullName
        nameTextView.text = account.fullName
        emailTextView.text = account.email
        addressTextView.text = account.address
        phoneTextView.text = account.phone
        genderTextView.text = account.gender
        birthdayTextView.text = account.dob

        // Payment list
        account.payments?.let {
            paymentList.clear()
            paymentList.addAll(it) }
        paymentRcView.adapter?.notifyDataSetChanged()
    }

    override fun onGetUserFail(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateUserPaymentSuccess(account: Account) {
        TODO("Not yet implemented")
    }

    override fun onUpdateUserPaymentFail(message: String) {
        TODO("Not yet implemented")
    }
}