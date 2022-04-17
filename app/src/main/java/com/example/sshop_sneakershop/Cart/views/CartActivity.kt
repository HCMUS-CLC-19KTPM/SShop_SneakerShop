package com.example.sshop_sneakershop.Cart

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Auth.views.AuthActivity
import com.example.sshop_sneakershop.Cart.controllers.CartController
import com.example.sshop_sneakershop.Cart.models.Cart
import com.example.sshop_sneakershop.Cart.views.CartAdapter
import com.example.sshop_sneakershop.Cart.views.CartClickListener
import com.example.sshop_sneakershop.Cart.views.CheckoutActivity
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity(), CartClickListener {

    private lateinit var cartController: CartController

    private lateinit var cart: Cart
    private val productsInCart = ArrayList<Product>()

    private lateinit var productRecyclerView: RecyclerView
    private lateinit var totalPriceTextView: TextView
    private lateinit var checkoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartController = CartController()
        getCart()

        productRecyclerView = findViewById(R.id.cart_recycler_view)
        totalPriceTextView = findViewById(R.id.cart_tv_total_price)
        checkoutButton = findViewById(R.id.cart_button_confirm)

        val mainActivity = this
        productRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = CartAdapter(productsInCart, mainActivity)
        }

        checkoutButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()

        val auth = Firebase.auth
        if (auth.currentUser == null || !auth.currentUser!!.isEmailVerified) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        updateProductList()
    }

    override fun onStop() {
        super.onStop()
        updateProductList()
    }

    override fun onDestroy() {
        super.onDestroy()
        updateProductList()
    }

    /**
     * Click on product in cart
     */
    override fun onClick(cart: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("item-id", cart.id)
        startActivity(intent)
    }

    /**
     * On change quantity of product in cart
     */
    @SuppressLint("SetTextI18n")
    override fun onChangeQuantity(position: Int, quantity: Int) {
        val format: NumberFormat = NumberFormat.getInstance()
        format.maximumFractionDigits = 2

        cart.productList?.get(position)!!.quantity = quantity
        cart.calculateTotalCost()

        totalPriceTextView.text = "${format.format(cart.totalCost)}$"
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getCart() {
        GlobalScope.launch(Dispatchers.Main) {
            cart = cartController.getCartByUser()!!

            if (cart.productList != null) {
                productsInCart.addAll(cart.productList!!)
                productRecyclerView.adapter?.notifyDataSetChanged()
                totalPriceTextView.text = cart.totalCost.toString()
            }

            if (productsInCart.isEmpty()) {
                totalPriceTextView.text = "0"
            } else {
                onChangeQuantity(0, productsInCart[0].quantity)
            }
        }
    }

    private fun updateProductList() {
        GlobalScope.launch(Dispatchers.Main) {
            cartController.updateProductList(cart)
        }
    }
}