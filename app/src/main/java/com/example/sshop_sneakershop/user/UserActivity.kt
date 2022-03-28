package com.example.sshop_sneakershop.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.gridlayout.widget.GridLayout
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
    }
}