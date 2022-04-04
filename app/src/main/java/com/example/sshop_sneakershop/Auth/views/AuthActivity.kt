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
import com.example.sshop_sneakershop.Homepage.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity(), IAuthView {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var signInWithGoogleBtn: Button

    private lateinit var controller: AuthController

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
        signInButton = findViewById(R.id.signin_button_login)
        signUpButton = findViewById(R.id.signin_button_register)
        signInWithGoogleBtn = findViewById(R.id.signin_button_register_with_google)

        signInButton.setOnClickListener {
            controller.onSignIn(emailEditText.text.toString(), passwordEditText.text.toString())
        }

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpViewActivity::class.java))
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, HomeActivity::class.java))
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

    override fun onSignUpSuccess() {
        TODO("Not yet implemented")
    }

    override fun onSignUpFailed(message: String) {
        TODO("Not yet implemented")
    }
}