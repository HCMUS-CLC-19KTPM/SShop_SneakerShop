package com.example.sshop_sneakershop.Auth.views

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sshop_sneakershop.Auth.controllers.AuthController
import com.example.sshop_sneakershop.Homepage.Home
import com.example.sshop_sneakershop.R

class AccountChangePassActivity : AppCompatActivity(), IAuthView {
    private lateinit var authController: AuthController

    private lateinit var changePasswordButton: Button
    private lateinit var oldPassEditText: EditText
    private lateinit var newPassEditText: EditText
    private lateinit var confirmNewPassEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_change_pass)

        authController = AuthController(this)

        changePasswordButton = findViewById(R.id.changePass_button_change)
        oldPassEditText = findViewById(R.id.changePass_input_oldPass)
        newPassEditText = findViewById(R.id.changePass_input_newPass)
        confirmNewPassEditText = findViewById(R.id.changePass_input_confirm_newPass)


        changePasswordButton.setOnClickListener {
            val oldPass = oldPassEditText.text.toString()
            val newPass = newPassEditText.text.toString()
            val confirmNewPass = confirmNewPassEditText.text.toString()

            if (oldPass.isEmpty() || newPass.isEmpty() || confirmNewPass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else{
                authController.onChangePassword(oldPass, newPass, confirmNewPass)
            }
        }

    }

    override fun onLoginSuccess(message: String) {
        TODO("Not yet implemented")
    }


    override fun onLoginFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onSignUpSuccess() {
        TODO("Not yet implemented")
    }

    override fun onSignUpFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onForgotPasswordSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onForgotPasswordFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onChangePasswordSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onChangePasswordFailed(message: String) {
        Log.d("AccountChangePassActivity", message)
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
//                    Log.d("nlhdung", "OK!")
                })
                // Set other dialog properties
                setIcon(android.R.drawable.ic_dialog_alert)
                setTitle(message)
            }
            // Create the AlertDialog
            builder.create()
        }
        if (alertDialog != null) {
            alertDialog.show()
        }
    }


}