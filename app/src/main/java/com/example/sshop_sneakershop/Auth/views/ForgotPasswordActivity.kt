package com.example.sshop_sneakershop.Auth.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Auth.controllers.AuthController
import com.example.sshop_sneakershop.R

class ForgotPasswordActivity : AppCompatActivity(), IForgotPasswordActivity {

    private lateinit var authController: AuthController

    private lateinit var emailTextEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        authController = AuthController(this)

        emailTextEditText = findViewById(R.id.forgot_password_input_email)
        sendButton = findViewById(R.id.forgot_password_button_send)

        sendButton.setOnClickListener {
            authController.onForgotPassword(emailTextEditText.text.toString())
        }
    }

    override fun onForgotPasswordSuccess(message: String) {
        Log.d("ForgotPasswordActivity", message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    override fun onForgotPasswordFailed(message: String) {
        Log.d("ForgotPasswordActivity", message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}