package com.example.sshop_sneakershop.Account.views

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.sshop_sneakershop.R

class AccountChangePassActivity : AppCompatActivity() {
    private lateinit var changePasswordButton: Button
    private lateinit var oldPassEditText: EditText
    private lateinit var newPassEditText: EditText
    private lateinit var confirmNewPassEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_change_pass)

        changePasswordButton = findViewById(R.id.changePass_button_change)
        oldPassEditText = findViewById(R.id.changePass_edittext_input_oldPass)
        newPassEditText = findViewById(R.id.changePass_edittext_input_newPass)
        confirmNewPassEditText = findViewById(R.id.changePass_edittext_confirm_newPass)


        changePasswordButton.setOnClickListener {
            if (oldPassEditText.text.toString() == "123456" && newPassEditText.text.toString() == confirmNewPassEditText.text.toString()) {
                AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Đổi mật khẩu thành công")
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        finish()
                    })
                    .show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Đổi mật khẩu thất bại")
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        finish()
                    })
                    .show()
            }


            val alertDialog: AlertDialog? = this.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
//                    Log.d("nlhdung", "OK!")
                    })
                    setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
//                    Log.d("nlhdung", "Canceled!")
                    })
                    // Set other dialog properties
                    setIcon(android.R.drawable.ic_dialog_alert)
                    setTitle("Change Password")
                }
                // Create the AlertDialog
                builder.create()
            }
            if (alertDialog != null) {
                alertDialog.show()
            }
        }


    }
}