package com.example.sshop_sneakershop.Account.views

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Auth.views.AccountChangePassActivity
import com.example.sshop_sneakershop.Auth.views.AuthActivity
import com.example.sshop_sneakershop.ChangePasswordFragment
import com.example.sshop_sneakershop.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountEditActivity : AppCompatActivity(), IAccountView {
    private val auth = Firebase.auth

    private val accountController = AccountController(this)

    private var account: Account? = null

    private lateinit var avatarImageView: CircularImageView
    private lateinit var usernameTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var dobEditText: EditText

    private lateinit var changePasswordButton: Button
    private lateinit var submitButton: Button

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        avatarImageView = findViewById(R.id.editProfile_image_avatar)
        usernameTextView = findViewById(R.id.editProfile_text_username)
        nameEditText = findViewById(R.id.editProfile_textInputET_name)
        emailEditText = findViewById(R.id.editProfile_textInputET_email)
        addressEditText = findViewById(R.id.editProfile_textInputET_address)
        phoneEditText = findViewById(R.id.editProfile_textInputET_phone)
        genderEditText = findViewById(R.id.editProfile_textInputET_gender)
        dobEditText = findViewById(R.id.editProfile_textInputET_dob)

        changePasswordButton = findViewById(R.id.edit_profile_button_change_password)
        submitButton = findViewById(R.id.edit_profile_button_submit)

        changePasswordButton.setOnClickListener {
            val intent = Intent(this, AccountChangePassActivity::class.java)
            startActivity(intent)
        }

        submitButton.setOnClickListener {
            updateUserInfo()
        }

        accountController.onGetUser(auth.currentUser!!.email!!)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getUserInfo() {
        GlobalScope.launch(Dispatchers.Main) {
            account = accountController.getUser(auth.currentUser!!.email!!)

            if (account != null) {
                if (!TextUtils.isEmpty(account!!.avatar)) Picasso.get()
                    .load(account!!.avatar).into(avatarImageView)
                usernameTextView.text = account!!.email
                nameEditText.setText(account!!.fullName)
                emailEditText.setText(account!!.email)
                addressEditText.setText(account!!.address)
                phoneEditText.setText(account!!.phone)
                genderEditText.setText(account!!.gender)
                dobEditText.setText(account!!.dob)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateUserInfo() {
        val fullName = nameEditText.text.toString()
        val email = usernameTextView.text.toString()
        val address = addressEditText.text.toString()
        val phone = phoneEditText.text.toString()
        val gender = genderEditText.text.toString()
        val dob = dobEditText.text.toString()

        if (account != null) {
            account!!.fullName = fullName
            account!!.email = email
            account!!.address = address
            account!!.phone = phone
            account!!.gender = gender
            account!!.dob = dob

            accountController.onUpdateUserInfo(account!!)
        }
    }

    override fun onGetUserSuccess(account: Account) {
        this.account = account

        if (!TextUtils.isEmpty(account.avatar)) Picasso.get()
            .load(account.avatar).into(avatarImageView)
        usernameTextView.text = account.email
        nameEditText.setText(account.fullName)
        emailEditText.setText(account.email)
        addressEditText.setText(account.address)
        phoneEditText.setText(account.phone)
        genderEditText.setText(account.gender)
        dobEditText.setText(account.dob)
    }

    override fun onGetUserFail(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateUserInfoSuccess(account: Account) {
        if (!TextUtils.isEmpty(account.avatar)) Picasso.get()
            .load(account.avatar).into(avatarImageView)
        usernameTextView.text = account.email
        nameEditText.setText(account.fullName)
        emailEditText.setText(account.email)
        addressEditText.setText(account.address)
        phoneEditText.setText(account.phone)
        genderEditText.setText(account.gender)
        dobEditText.setText(account.dob)
    }

    override fun onUpdateUserInfoFail(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateUserPaymentSuccess(account: Account) {
        TODO("Not yet implemented")
    }

    override fun onUpdateUserPaymentFail(message: String) {
        TODO("Not yet implemented")
    }
}