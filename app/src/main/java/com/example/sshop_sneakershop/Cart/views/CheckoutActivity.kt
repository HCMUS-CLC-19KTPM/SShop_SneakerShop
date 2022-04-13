package com.example.sshop_sneakershop.Cart.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.Product.views.ProductItemAdapter
import com.example.sshop_sneakershop.R

class CheckoutActivity : AppCompatActivity() {
    lateinit var products: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Lookup the recyclerview in activity layout
        val productRecyclerView = findViewById<RecyclerView>(R.id.checkout_recycler_view)

        val myProduct = Product("",100.0, "Shoe", "", "Description", ArrayList())
        products = listOf(myProduct, myProduct, myProduct, myProduct, myProduct, myProduct,myProduct, myProduct, myProduct).toCollection(ArrayList())

        val adapter = ProductItemAdapter(products)

        productRecyclerView.adapter = adapter

        productRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)

    }
}