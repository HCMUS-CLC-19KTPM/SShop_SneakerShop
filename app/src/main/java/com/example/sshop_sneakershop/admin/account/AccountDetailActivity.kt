package com.example.sshop_sneakershop.admin.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.sshop_sneakershop.R

class AccountDetailActivity : AppCompatActivity() {

    var username: EditText? = null
    var address: EditText? = null
    var phone: EditText? = null
    var gender: EditText? = null
    var birthday: EditText? = null
    var status: Spinner? = null
    var deleteBtn: Button? = null
    var submitBtn: Button? = null
    var editProfileTV: TextView? = null

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
            editProfileTV!!.setText("Editing Mode")
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
            editProfileTV!!.setText("Edit Profile")
        }

        deleteBtn!!.setOnClickListener{
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }
    }
}