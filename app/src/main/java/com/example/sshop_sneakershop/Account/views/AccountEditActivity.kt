package com.example.sshop_sneakershop.Account.views

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Auth.views.AuthActivity
import com.example.sshop_sneakershop.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AccountEditActivity : AppCompatActivity() {

    private val accountController = AccountController()
    private val auth = Firebase.auth

    private lateinit var avatarImageView: CircularImageView
    private lateinit var usernameTextView: TextView
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var dropdownGender: AutoCompleteTextView
    private lateinit var editTextDob: EditText

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

//        val spinnerPosition: Int = adapter.getPosition(StudentList.stuList[index].classroom)
//        inputClass.adapter = adapter
//        inputClass.setSelection(spinnerPosition)

        avatarImageView = findViewById(R.id.editProfile_image_avatar)
        usernameTextView = findViewById(R.id.editProfile_text_username)
        editTextName = findViewById(R.id.editProfile_textInputET_name)
        editTextEmail = findViewById(R.id.editProfile_textInputET_email)
        editTextAddress = findViewById(R.id.editProfile_textInputET_address)
        editTextPhone = findViewById(R.id.editProfile_textInputET_phone)
        dropdownGender = findViewById(R.id.editProfile_gender_dropdown)
        editTextDob = findViewById(R.id.editProfile_textInputET_dob)


        val type = arrayOf("Male", "Female", "Other")
        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_menu_popup_item,
            type
        )
        dropdownGender.setAdapter(adapter)

        getUserInfo()
    }

    private fun getUserInfo() {
        GlobalScope.launch(Dispatchers.Main) {
            val account = accountController.getUser(auth.currentUser!!.email!!)

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