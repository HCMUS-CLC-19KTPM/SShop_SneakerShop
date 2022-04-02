package com.example.sshop_sneakershop.Auth.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Auth.Controller.AuthController
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Homepage.HomeActivity

class SignUpViewActivity : AppCompatActivity(), IAuthView {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button

    private lateinit var controller: AuthController

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
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onSignUpFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoginSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLoginFailed(message: String) {
        TODO("Not yet implemented")
    }
}