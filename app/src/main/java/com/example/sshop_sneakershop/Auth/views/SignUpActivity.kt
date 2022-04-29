package com.example.sshop_sneakershop.Auth.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Auth.controllers.AuthController
import com.example.sshop_sneakershop.Auth.controllers.IAuthController
import com.example.sshop_sneakershop.Homepage.Home
import com.example.sshop_sneakershop.R

class SignUpActivity : AppCompatActivity(), ISignUpActivity {
    private lateinit var controller: IAuthController

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        controller = AuthController(this)

        emailEditText = findViewById(R.id.signup_input_email)
        passwordEditText = findViewById(R.id.signup_input_password)
        confirmPasswordEditText = findViewById(R.id.signup_input_confirm_password)
        signUpButton = findViewById(R.id.signup_button_register)

        signUpButton.setOnClickListener {
            controller.onSignUp(
                emailEditText.text.toString(),
                passwordEditText.text.toString(),
                confirmPasswordEditText.text.toString()
            )
        }
    }

    override fun onSignUpSuccess() {
        startActivity(Intent(this, Home::class.java))
        finish()
    }

    override fun onSignUpFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}