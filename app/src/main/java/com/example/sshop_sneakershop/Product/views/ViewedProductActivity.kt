package com.example.sshop_sneakershop.Product.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sshop_sneakershop.databinding.ActivityViewedProductBinding

class ViewedProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewedProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewedProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}