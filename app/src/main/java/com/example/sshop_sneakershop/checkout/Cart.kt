package com.example.sshop_sneakershop.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.R

class Cart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val productRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val myProduct = ProductItem("",100.0, "Shoe", "image url", "Description", 2)
        val products = listOf(myProduct, myProduct, myProduct,myProduct,myProduct,myProduct
                ,myProduct,myProduct)

        val adapter = ProductItemAdapter(products)

        productRecyclerView.adapter = adapter

        productRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)

    }
}