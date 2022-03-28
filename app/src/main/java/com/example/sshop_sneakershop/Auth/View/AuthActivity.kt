package com.example.sshop_sneakershop.Auth.View

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Auth.Controller.AuthController
import com.example.sshop_sneakershop.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity(), IAuthView {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var googleButton: Button

    private lateinit var controller: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        emailEditText = findViewById(R.id.signin_input_email)
        passwordEditText = findViewById(R.id.signin_input_password)
        loginButton = findViewById(R.id.signin_button_login)
        registerButton = findViewById(R.id.signin_button_register)
        googleButton = findViewById(R.id.signin_button_register_with_google)

        controller = AuthController(this)

        loginButton.setOnClickListener {
            controller.onLogin(emailEditText.text.toString(), passwordEditText.text.toString())
        }

        registerButton.setOnClickListener {
            controller.onRegister(emailEditText.text.toString(), passwordEditText.text.toString())
        }

        googleButton.setOnClickListener {
            val auth = Firebase.auth
            val user = auth.currentUser
            Log.i("hehe", "user: ${user!!.email}")
        }
    }

    override fun onLoginSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val auth = Firebase.auth
        val user = auth.currentUser
        Log.i("hehe", "user: $user")
    }

    override fun onLoginFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}