package com.example.sshop_sneakershop.Cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.Product.views.ProductItemAdapter
import com.example.sshop_sneakershop.R

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val productRecyclerView = findViewById<RecyclerView>(R.id.cart_recycler_view)

        val myProduct = Product("",100.0, "Shoe", "image url", "Description", ArrayList())
        val products = listOf(myProduct, myProduct, myProduct,myProduct,myProduct,myProduct
                ,myProduct,myProduct)

        val adapter = ProductItemAdapter(products)

        productRecyclerView.adapter = adapter

        productRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)

    }
}