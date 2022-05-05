package com.example.sshop_sneakershop.Cart.views

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Account.views.AccountActivity
import com.example.sshop_sneakershop.Account.views.AddNewCustomerInfoDialog
import com.example.sshop_sneakershop.Account.views.AddPaymentActivity
import com.example.sshop_sneakershop.Account.views.ChoosePaymentMethodDialog
import com.example.sshop_sneakershop.Cart.controllers.CartController
import com.example.sshop_sneakershop.Cart.models.Cart
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Order.views.IOrderListActivity
import com.example.sshop_sneakershop.Order.views.OrderListActivity
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.CustomProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.databinding.ActivityCheckoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class CheckoutActivity : AppCompatActivity(), ICartView, IOrderListActivity, ItemClickListener,
    AddNewCustomerInfoDialog.BottomSheetListener {
    private lateinit var cartController: CartController
    private lateinit var orderController: OrderController
    private lateinit var accountController: AccountController

    private val productsInCart = ArrayList<Product>()
    private lateinit var cart: Cart

    private lateinit var binding: ActivityCheckoutBinding
    private var customerInfo: ArrayList<String> = arrayListOf("", "", "","")

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val checkoutActivity = this
        binding.checkoutRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = CustomProductAdapter(productsInCart, checkoutActivity)
        }


        cartController = CartController(this)
        cartController.onGetCart()

        orderController = OrderController(this)
        accountController = AccountController()

        binding.checkoutButtonCurrentInfo.setOnClickListener {
            getCurrentCustomerInfo()
        }
        binding.checkoutButtonAddNewInfo.setOnClickListener {
            addNewCustomerInfo()
        }
        binding.checkoutButtonCurrentPaymentMethod.setOnClickListener {
            getCurrentPaymentMethod()
        }
        binding.checkoutButtonAddPaymentMethod.setOnClickListener {
            addPaymentMethod()
        }
        binding.checkoutButtonConfirm.setOnClickListener {
            confirmOrder()
        }
        binding.checkoutToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getCurrentPaymentMethod() {
        val choosePaymentMethodDialog = ChoosePaymentMethodDialog()
        choosePaymentMethodDialog.show(supportFragmentManager, "ChoosePaymentMethodDialog")
    }

    private fun addPaymentMethod() {
        val intent = Intent(this, AddPaymentActivity::class.java)
        startActivity(intent)
    }

    private fun confirmOrder() {
        if(customerInfo[0] == "" || customerInfo[1] == "" || customerInfo[2] == "" || customerInfo[3] == "") {
            MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage("Please fill in all the information")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            // Create order
            orderController.onCreateOrder(
                customerInfo[0],
                customerInfo[1],
                customerInfo[2],
                productsInCart,
                cart.totalCost,
                customerInfo[3]
            )
        }
    }

    private fun addNewCustomerInfo() {
        // get current account id and add new full name, phone and address
        val addNewInfoFragment = AddNewCustomerInfoDialog()
        val bundle = Bundle()
        bundle.putStringArrayList("newCustomerInfo", customerInfo)
        addNewInfoFragment.arguments = bundle
        addNewInfoFragment.show(supportFragmentManager, "addNewInfo")
    }

    private fun getCurrentCustomerInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val account = accountController.getUser()!!
            withContext(Dispatchers.Main) {
                // check valid fullName, phone, address, id
                if (account.fullName.isNullOrEmpty() || account.phone.isNullOrEmpty() || account.address.isNullOrEmpty()) {
                    MaterialAlertDialogBuilder(this@CheckoutActivity)
                        .setTitle("Error")
                        .setMessage("Invalid customer information. You will be redirected to profile page to update information.")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(this@CheckoutActivity, AccountActivity::class.java)
                            startActivity(intent)
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                } else {
                    // assign value to customer info
                    customerInfo[0] = account.fullName!!
                    customerInfo[1] = account.phone!!
                    customerInfo[2] = account.address!!
                    customerInfo[3] = account.id
                    MaterialAlertDialogBuilder(this@CheckoutActivity)
                        .setTitle("Success")
                        .setMessage("Customer's information has been updated")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    private fun getCart() {
        GlobalScope.launch(Dispatchers.Main) {
            val cart = cartController.getCart()

            if (cart != null) {
                productsInCart.addAll(cart.productList)
                binding.checkoutRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onGetCartSuccess(cart: Cart) {
        this.cart = cart
        productsInCart.addAll(cart.productList)
        binding.checkoutRecyclerView.adapter?.notifyDataSetChanged()
        // Format price
        val format: NumberFormat = NumberFormat.getInstance()
        format.maximumFractionDigits = 2
        // Update UI
        binding.checkoutTvTotalPrice.text = "${format.format(cart.totalCost)}$"
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
        MaterialAlertDialogBuilder(this)
            .setTitle("Success")
            .setMessage("Your order has been created successfully.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(this, OrderListActivity::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }

    override fun onCreateOrderFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("item-id", product.id)
        startActivity(intent)
    }

    override fun onButtonClicked(values: ArrayList<String>) {
        customerInfo.clear()
        customerInfo.addAll(values)
        CoroutineScope(Dispatchers.IO).launch {
            val account = accountController.getUser()!!
            withContext(Dispatchers.Main) {
                if (customerInfo.size != 0) {
                    customerInfo[3] = account.id
                    Log.i("new-customer-info", customerInfo.toString())
                    MaterialAlertDialogBuilder(this@CheckoutActivity)
                        .setTitle("Success")
                        .setMessage("Customer's information has been updated.")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }
}