package com.example.sshop_sneakershop.Cart.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Cart.controllers.CartController
import com.example.sshop_sneakershop.Cart.models.Cart
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Order.views.IOrderListActivity
import com.example.sshop_sneakershop.Order.views.OrderListActivity
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductItemAdapter
import com.example.sshop_sneakershop.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CheckoutActivity : AppCompatActivity(), ICartView, IOrderListActivity {
    private lateinit var cartController: CartController
    private lateinit var orderController: OrderController
    private lateinit var accountController: AccountController

    private lateinit var account: Account
    private val productsInCart = ArrayList<Product>()
    private lateinit var cart: Cart

    private lateinit var productRecyclerView: RecyclerView

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Lookup the recyclerview in activity layout
        productRecyclerView = findViewById(R.id.checkout_recycler_view)

        productRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CheckoutActivity)
            adapter = ProductItemAdapter(productsInCart)
        }

        val getCurrentInfoButton = findViewById<Button>(R.id.checkout_button_current_info)
        getCurrentInfoButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                account = accountController.getUser()!!
            }
        }

        val checkoutButton = findViewById<Button>(R.id.cart_button_confirm)
        checkoutButton.setOnClickListener {
            // Create order
            GlobalScope.launch(Dispatchers.Main) {
                orderController.onCreateOrder(
                    Order(
                        "",
                        account.fullName!!,
                        account.phone!!,
                        account.address!!,
                        productsInCart,
                        "Chờ xác nhận",
                        Date(),
                        Date(),
                        Date(),
                        0.0,
                        cart.totalCost,
                        account.id
                    )
                )
            }
        }

        cartController = CartController(this)
        cartController.onGetCart()

        orderController = OrderController(this)
        accountController = AccountController()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    private fun getCart() {
        GlobalScope.launch(Dispatchers.Main) {
            val cart = cartController.getCartByUser()

            if (cart != null) {
                productsInCart.addAll(cart.productList)
                productRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetCartSuccess(cart: Cart) {
        this.cart = cart
        productsInCart.addAll(cart.productList)
        productRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onGetCartFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetAllOrdersSuccess(orders: ArrayList<Order>) {
        TODO("Not yet implemented")
    }

    override fun onGetAllOrdersFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateOrderSuccess(order: Order) {
        val intent = Intent(this, OrderListActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreateOrderFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}