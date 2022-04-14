package com.example.sshop_sneakershop.Cart

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Cart.controllers.CartController
import com.example.sshop_sneakershop.Cart.views.CartAdapter
import com.example.sshop_sneakershop.Cart.views.CartClickListener
import com.example.sshop_sneakershop.Cart.views.CheckoutActivity
import com.example.sshop_sneakershop.Product.models.ProductInCart
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity(), CartClickListener {

    private lateinit var cartController: CartController
    private val productsInCart = ArrayList<ProductInCart>()

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

    override fun onClick(cart: ProductInCart) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("item-id", cart.id)
        startActivity(intent)
    }

    private fun getCart() {
        GlobalScope.launch(Dispatchers.Main) {
            val cart = cartController.getCartByUser()

            if (cart.productList != null) {
                productsInCart.addAll(cart.productList!!)
                productRecyclerView.adapter?.notifyDataSetChanged()
                totalPriceTextView.text = cart.totalCost.toString()
            }
        }
    }
}