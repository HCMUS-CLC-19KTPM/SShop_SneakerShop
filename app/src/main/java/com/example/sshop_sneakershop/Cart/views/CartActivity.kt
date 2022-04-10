package com.example.sshop_sneakershop.Cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Cart.views.CartAdapter
import com.example.sshop_sneakershop.Cart.views.CartClickListener
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.Product.views.ProductItemAdapter
import com.example.sshop_sneakershop.R

class CartActivity : AppCompatActivity(),CartClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val productRecyclerView = findViewById<RecyclerView>(R.id.cart_recycler_view)

        val myProduct = Cart("1",100.0, "AIR JORDAN 1 LOW OLIVE MOYEN", "", 15, "US6")
        val products = listOf(myProduct, myProduct, myProduct)

        val mainActivity = this
        productRecyclerView.apply{
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = CartAdapter(products,mainActivity)
        }

    }
    override fun onClick(cart: Cart) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("item-id", cart.id)
        startActivity(intent)
    }
}