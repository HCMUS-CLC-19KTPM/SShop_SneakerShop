package com.example.sshop_sneakershop.Account.views

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Account.controllers.IAccountController
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Auth.views.AccountChangePassActivity
import com.example.sshop_sneakershop.Auth.views.SignInActivity
import com.example.sshop_sneakershop.Cart.CartActivity
import com.example.sshop_sneakershop.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    private lateinit var accountController: IAccountController

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
    private lateinit var toolbar: MaterialToolbar
    private lateinit var changeAvatarBtn: FloatingActionButton
    private var imageUri: Uri? = null

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        accountController = AccountController(this, this)

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
        toolbar = findViewById(R.id.editProfile_toolbar)
        changeAvatarBtn = findViewById(R.id.editProfile_button_change_avatar)

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
        changeAvatarBtn.setOnClickListener {
            selectAvatar()
        }

        submitButton.setOnClickListener {
//            updateUserInfo()
            if (imageUri!=null){
                imageUri?.let { accountController.onUpdateAvatar(it) }
            }else{
                updateUserInfo()
            }

        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                }
            }
            true
        }
        accountController.onGetUser()
    }
    fun selectAvatar(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            avatarImageView.setImageURI(imageUri)
        }
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

            Log.i("image-url", "2: ${account!!.avatar}")
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

        finish()
    }

    override fun onUpdateUserInfoFailed(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        MaterialAlertDialogBuilder(this)
            .setTitle("Success")
            .setMessage("Update profile successfully!")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }


    override fun onUpdateAvatarSuccess(imageURL: String) {
        account!!.avatar = imageURL
        updateUserInfo()
    }

    override fun onUpdateAvatarFail(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateUserPaymentSuccess(account: Account) {
        TODO("Not yet implemented")
    }

    override fun onUpdateUserPaymentFail(message: String) {
        TODO("Not yet implemented")
    }
}