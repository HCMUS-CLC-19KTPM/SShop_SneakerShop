package com.example.sshop_sneakershop.Account.views

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Auth.views.AuthActivity
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.User.views.EditPaymentActivity
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

    private lateinit var avatarImageView: CircularImageView
    private lateinit var usernameTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var genderTextView: TextView
    private lateinit var birthdayTextView: TextView

    private fun getAccountsProfile() {

        avatarImageView = findViewById(R.id.profile_image_avatar)
        usernameTextView = findViewById(R.id.profile_text_username)
        nameTextView = findViewById(R.id.profile_text_valueName)
        emailTextView = findViewById(R.id.profile_text_valueEmail)
        addressTextView = findViewById(R.id.profile_text_valueAddress)
        phoneTextView = findViewById(R.id.profile_text_valuePhone)
        genderTextView = findViewById(R.id.profile_text_valueGender)
        birthdayTextView = findViewById(R.id.profile_text_valueDob)

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

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        // Get account's profile
        getAccountsProfile()

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

        val editPaymentBtn = findViewById<Button>(R.id.profile_button_editPayment)
        editPaymentBtn.setVisibility(View.GONE)

        val paymentRcView = findViewById<RecyclerView>(R.id.recyclerView)
        paymentRcView.setVisibility(View.GONE)

        val showPaymentBtn = findViewById<Button>(R.id.profile_button_showPayment)
        showPaymentBtn.setOnClickListener {
            if(paymentRcView.getVisibility() == View.GONE) {
                paymentRcView.setVisibility(View.VISIBLE)
                editPaymentBtn.setVisibility(View.VISIBLE)
            } else {
                paymentRcView.setVisibility(View.GONE)
                editPaymentBtn.setVisibility(View.GONE)
            }
        }

        editPaymentBtn.setOnClickListener {
            val intent = Intent(this, EditPaymentActivity::class.java)
            startActivity(intent)
        }
    }
}