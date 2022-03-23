package com.example.sshop_sneakershop.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.R

class CheckoutActivity : AppCompatActivity() {
    lateinit var products: ArrayList<ProductItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Lookup the recyclerview in activity layout
        val productRecyclerView = findViewById<RecyclerView>(R.id.checkout_recycler_view) as RecyclerView

        val myProduct = ProductItem(100.0, "Shoe", "image url", "Description", 2)
        products = listOf(myProduct, myProduct, myProduct).toCollection(ArrayList())

        val adapter = ProductItemAdapter(products)

        productRecyclerView.adapter = adapter

        productRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)
    }
}