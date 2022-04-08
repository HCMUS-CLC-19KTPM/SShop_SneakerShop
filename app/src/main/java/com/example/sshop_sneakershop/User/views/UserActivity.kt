package com.example.sshop_sneakershop.User.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.gridlayout.widget.GridLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.R

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val editInfoBtn = findViewById<Button>(R.id.profile_button_editInfo)
        editInfoBtn.setVisibility(View.GONE)

        val infoView = findViewById<GridLayout>(R.id.gridLayout)
        infoView.setVisibility(View.GONE)

        val showInfoBtn = findViewById<Button>(R.id.profile_button_showAccDetail)
        showInfoBtn.setOnClickListener {
            if(infoView.getVisibility() == View.GONE) {
                infoView.setVisibility(View.VISIBLE)
                editInfoBtn.setVisibility(View.VISIBLE)
            } else {
                infoView.setVisibility(View.GONE)
                editInfoBtn.setVisibility(View.GONE)
            }
        }

        editInfoBtn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        val editPaymentBtn = findViewById<Button>(R.id.profile_button_editPayment)
        editPaymentBtn.setVisibility(View.GONE)

        val paymentRcView = findViewById<RecyclerView>(R.id.recyclerView)
        paymentRcView.setVisibility(View.GONE)

        val showPaymentBtn = findViewById<Button>(R.id.profile_button_showPayment)
        showPaymentBtn.setOnClickListener {
            if(paymentRcView.getVisibility() == View.GONE) {
                paymentRcView.setVisibility(View.VISIBLE)
                editPaymentBtn.setVisibility(View.VISIBLE)
            } else {
                paymentRcView.setVisibility(View.GONE)
                editPaymentBtn.setVisibility(View.GONE)
            }
        }

        editPaymentBtn.setOnClickListener {
            val intent = Intent(this, EditPaymentActivity::class.java)
            startActivity(intent)
        }



    }
}