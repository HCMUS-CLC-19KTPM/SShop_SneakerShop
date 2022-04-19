package com.example.sshop_sneakershop.Cart

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Auth.views.AuthActivity
import com.example.sshop_sneakershop.Cart.controllers.CartController
import com.example.sshop_sneakershop.Cart.models.Cart
import com.example.sshop_sneakershop.Cart.views.CartAdapter
import com.example.sshop_sneakershop.Cart.views.CartClickListener
import com.example.sshop_sneakershop.Cart.views.CheckoutActivity
import com.example.sshop_sneakershop.Cart.views.ICartView
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationBarMenu
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity(), CartClickListener, ICartView {

    private lateinit var cartController: CartController

    private lateinit var cart: Cart
    private val productsInCart = ArrayList<Product>()

    private lateinit var productRecyclerView: RecyclerView
    private lateinit var totalPriceTextView: TextView
    private lateinit var checkoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

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
            updateProductList()

            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }

        cartController = CartController(this)
        cartController.onGetCart()

        findViewById<MaterialToolbar>(R.id.cart_toolbar).setNavigationOnClickListener {
            finish()
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

        cart.productList[position].quantity = quantity
        cart.calculateTotalCost()

        totalPriceTextView.text = "${format.format(cart.totalCost)}$"
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    private fun getCart() {
        GlobalScope.launch(Dispatchers.Main) {
            cart = cartController.getCart()!!

            productsInCart.addAll(cart.productList)
            productRecyclerView.adapter?.notifyDataSetChanged()
            totalPriceTextView.text = cart.totalCost.toString()

            if (productsInCart.isEmpty()) {
                totalPriceTextView.text = "0"
            } else {
                onChangeQuantity(0, productsInCart[0].quantity)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateProductList() {
        GlobalScope.launch(Dispatchers.Main) {
            cartController.updateProductList(cart)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetCartSuccess(cart: Cart) {
        this.cart = cart

        productsInCart.clear()
        productsInCart.addAll(this.cart.productList)
        productRecyclerView.adapter?.notifyDataSetChanged()
        totalPriceTextView.text = this.cart.totalCost.toString()

        if (productsInCart.isEmpty()) {
            totalPriceTextView.text = "0"
        } else {
            onChangeQuantity(0, productsInCart[0].quantity)
        }
    }

    override fun onGetCartFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}