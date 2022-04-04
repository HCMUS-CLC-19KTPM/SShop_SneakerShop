package com.example.sshop_sneakershop.Admin.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.sshop_sneakershop.R

class AccountDetailActivity : AppCompatActivity() {

    private var username: EditText? = null
    private var address: EditText? = null
    private var phone: EditText? = null
    private var gender: EditText? = null
    private var birthday: EditText? = null
    private var status: Spinner? = null
    private var deleteBtn: Button? = null
    private var submitBtn: Button? = null
    private var editProfileTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_account_detail)

        username = findViewById(R.id.admin_account_detail_textview_username_2)
        address = findViewById(R.id.admin_account_detail_textview_address)
        phone = findViewById(R.id.admin_account_detail_textview_phone)
        gender = findViewById(R.id.admin_account_detail_textview_gender)
        birthday = findViewById(R.id.admin_account_detail_textview_birthday)
        status = findViewById(R.id.admin_account_detail_spinner_status)
        deleteBtn = findViewById(R.id.admin_account_detail_button_delete)
        submitBtn = findViewById(R.id.admin_account_detail_button_confirm)
        editProfileTV = findViewById(R.id.admin_account_detail_textview_edit_profile)

        editProfileTV!!.setOnClickListener {
            username!!.isFocusableInTouchMode = true
            address!!.isFocusableInTouchMode = true
            phone!!.isFocusableInTouchMode = true
            gender!!.isFocusableInTouchMode=true
            birthday!!.isFocusableInTouchMode=true
            editProfileTV!!.text = "Editing Mode"
        }
        submitBtn!!.setOnClickListener {
            username!!.clearFocus()
            address!!.clearFocus()
            phone!!.clearFocus()
            gender!!.clearFocus()
            birthday!!.clearFocus()

            username!!.isFocusableInTouchMode = false
            address!!.isFocusableInTouchMode = false
            phone!!.isFocusableInTouchMode = false
            gender!!.isFocusableInTouchMode=false
            birthday!!.isFocusableInTouchMode=false
            editProfileTV!!.text = "Edit Profile"
        }

        deleteBtn!!.setOnClickListener{
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }
    }
}