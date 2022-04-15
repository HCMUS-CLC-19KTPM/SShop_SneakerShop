package com.example.sshop_sneakershop.Cart.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Cart.controllers.CartController
import com.example.sshop_sneakershop.Order.views.OrderListActivity
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductItemAdapter
import com.example.sshop_sneakershop.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CheckoutActivity : AppCompatActivity() {
    private lateinit var cartController: CartController
    private val productsInCart = ArrayList<Product>()
    private lateinit var productRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        cartController = CartController()
        getCart()
        // Lookup the recyclerview in activity layout
        productRecyclerView = findViewById(R.id.checkout_recycler_view)

        productRecyclerView.apply{
            layoutManager = LinearLayoutManager(this@CheckoutActivity)
            adapter = ProductItemAdapter(productsInCart)
        }

        val checkoutButton = findViewById<Button>(R.id.cart_button_confirm)
        checkoutButton.setOnClickListener {

            val intent = Intent(this, OrderListActivity::class.java)
            startActivity(intent)
            finish()
            TODO("Push order to server")
        }
    }
    private fun getCart() {
        GlobalScope.launch(Dispatchers.Main) {
            val cart = cartController.getCartByUser()

            if (cart != null) {
                if (cart.productList != null) {
                    productsInCart.addAll(cart.productList!!)
                    productRecyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }
    }
}