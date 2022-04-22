package com.example.sshop_sneakershop.Account.views

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Auth.views.AccountChangePassActivity
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
import java.util.*


class AccountEditActivity : AppCompatActivity(), IAccountActivity, IAccountEditActivity {
    private val auth = Firebase.auth

    private val accountController = AccountController(this, this)

    private var account: Account? = null

    private lateinit var avatarImageView: CircularImageView
    private lateinit var usernameTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var spinner: Spinner
    private lateinit var picker: DatePickerDialog

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
        spinner = findViewById(R.id.editProflie_spinner_gender)

        changePasswordButton = findViewById(R.id.edit_profile_button_change_password)
        submitButton = findViewById(R.id.edit_profile_button_submit)

        genderEditText.setOnClickListener { spinner.performClick() }
        val type = arrayOf("Male", "Female", "Other")
        ArrayAdapter(this, android.R.layout.simple_spinner_item, type)
            .also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
                )
                // Apply the adapter to the spinner
                val spinnerPosition: Int = adapter.getPosition(genderEditText.text.toString())
                spinner.adapter = adapter
                spinner.setSelection(spinnerPosition)
            }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                genderEditText.setText(parent?.getItemAtPosition(0).toString())
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                genderEditText.setText(parent?.getItemAtPosition(position).toString())
            }
        }

        dobEditText.setOnClickListener {
            picker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val year: Int = year
                    val month: Int = month + 1
                    val day: Int = dayOfMonth
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    dobEditText.setText("$day/$month/$year")

                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            picker.show()
            picker.datePicker.maxDate = System.currentTimeMillis()
        }


        changePasswordButton.setOnClickListener {
            val intent = Intent(this, AccountChangePassActivity::class.java)
            startActivity(intent)
        }

        submitButton.setOnClickListener {
            updateUserInfo()
        }

        accountController.onGetUser()
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

    override fun onUpdateUserInfoFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateUserPaymentSuccess(account: Account) {
        TODO("Not yet implemented")
    }

    override fun onUpdateUserPaymentFail(message: String) {
        TODO("Not yet implemented")
    }
}