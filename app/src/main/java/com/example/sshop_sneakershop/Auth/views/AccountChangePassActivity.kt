package com.example.sshop_sneakershop.Auth.views

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sshop_sneakershop.Auth.controllers.AuthController
import com.example.sshop_sneakershop.Auth.controllers.IAuthController
import com.example.sshop_sneakershop.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountChangePassActivity : AppCompatActivity(), IChangePasswordActivity {
    private lateinit var authController: IAuthController
    private val auth = Firebase.auth

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
                if (newPass != confirmNewPass) {
                    Toast.makeText(this, "New password and confirm new password are not match", Toast.LENGTH_SHORT).show()
                } else {
                    GlobalScope.launch(Dispatchers.Main) {
                        val account = auth.currentUser!!
                        val credential = EmailAuthProvider.getCredential(account.email!!, oldPass)
                        account.reauthenticate(credential).addOnCompleteListener{ task ->
                            if (task.isSuccessful) {
                                authController.onChangePassword(newPass)
                            } else {
                                Toast.makeText(this@AccountChangePassActivity, "Old password is not correct", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

    }

    override fun onChangePasswordSuccess(message: String) {
        Log.d("AccountChangePassActivity", message)
        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    finish()
                })
                // Set other dialog properties
                setIcon(R.drawable.ic_baseline_done_outline_24)
                setTitle(message)
            }
            // Create the AlertDialog
            builder.create()
        }
        if (alertDialog != null) {
            alertDialog.show()
        }    }

    override fun onChangePasswordFailed(message: String) {
        Log.d("AccountChangePassActivity", message)
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
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