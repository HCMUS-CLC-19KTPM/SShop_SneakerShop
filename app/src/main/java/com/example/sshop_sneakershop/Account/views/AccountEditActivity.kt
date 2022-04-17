package com.example.sshop_sneakershop.Account.views

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Auth.views.AuthActivity
import com.example.sshop_sneakershop.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountEditActivity : AppCompatActivity() {
    private val auth = Firebase.auth

    private val accountController = AccountController()

    private var account: Account? = null

    private lateinit var avatarImageView: CircularImageView
    private lateinit var usernameTextView: TextView
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var dropdownGender: AutoCompleteTextView
    private lateinit var editTextDob: EditText
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
        editTextName = findViewById(R.id.editProfile_textInputET_name)
        editTextEmail = findViewById(R.id.editProfile_textInputET_email)
        editTextAddress = findViewById(R.id.editProfile_textInputET_address)
        editTextPhone = findViewById(R.id.editProfile_textInputET_phone)
        dropdownGender = findViewById(R.id.editProfile_gender_dropdown)
        editTextDob = findViewById(R.id.editProfile_textInputET_dob)
        nameEditText = findViewById(R.id.editProfile_textInputET_name)
        emailEditText = findViewById(R.id.editProfile_textInputET_email)
        addressEditText = findViewById(R.id.editProfile_textInputET_address)
        phoneEditText = findViewById(R.id.editProfile_textInputET_phone)
        genderEditText = findViewById(R.id.editProfile_textInputET_gender)
        dobEditText = findViewById(R.id.editProfile_textInputET_dob)

        changePasswordButton = findViewById(R.id.edit_profile_button_change_password)
        submitButton = findViewById(R.id.edit_profile_button_submit)

        changePasswordButton.setOnClickListener {
            TODO("Change password")
        }

        submitButton.setOnClickListener {
            updateUserInfo()
        }

        getUserInfo()
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
        GlobalScope.launch(Dispatchers.Main) {
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

                account = accountController.updateUser(account!!)
            }
            if (!TextUtils.isEmpty(account.avatar)) Picasso.get()
                .load(account.avatar).into(avatarImageView)
            usernameTextView.text = account.email
            editTextName.setText(account.fullName)
            editTextEmail.setText(account.email)
            editTextAddress.setText(account.address)
            editTextPhone.setText(account.phone)
            editTextDob.setText(account.dob)

            val type = arrayOf("Male", "Female", "Other")
            val index = type.indexOf(account.gender)
            dropdownGender.setText(type.get(index))
        }
    }
}