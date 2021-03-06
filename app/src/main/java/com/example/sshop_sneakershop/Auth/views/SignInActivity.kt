package com.example.sshop_sneakershop.Auth.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Auth.controllers.AuthController
import com.example.sshop_sneakershop.Auth.controllers.IAuthController
import com.example.sshop_sneakershop.Homepage.Home
import com.example.sshop_sneakershop.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity(), ISignInActivity {
    private lateinit var controller: IAuthController

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var signInWithGoogleBtn: Button

    /**
     * Initialize the activity
     * @param savedInstanceState Bundle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        controller = AuthController(this)

        emailEditText = findViewById(R.id.signin_input_email)
        passwordEditText = findViewById(R.id.signin_input_password)
        forgotPasswordTextView = findViewById(R.id.signin_textview_forgot_password)
        signInButton = findViewById(R.id.signin_button_login)
        signUpButton = findViewById(R.id.signin_button_register)
        signInWithGoogleBtn = findViewById(R.id.signin_button_register_with_google)

        forgotPasswordTextView.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        signInButton.setOnClickListener {
            controller.onSignIn(emailEditText.text.toString(), passwordEditText.text.toString())
        }

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        googleSignIn()
    }

    /**
     * Sign in with google
     */
    private fun googleSignIn() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        signInWithGoogleBtn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, 100)
        }
    }

    /**
     * Handle the result of the google sign in
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent
     */
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                controller.onSignInWithGoogle(credential)
            }

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w("Google Sign In", "Google sign in failed", e)
            }
        }
    }

    /**
     * Show the toast
     * @param message
     */
    override fun onLoginSuccess(message: String) {
        Log.d("AuthActivity", message)

        startActivity(Intent(this, Home::class.java))
        finish()
    }

    /**
     * Show the toast
     * @param message
     */
    override fun onLoginFailed(message: String) {
        Log.d("AuthActivity", message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}