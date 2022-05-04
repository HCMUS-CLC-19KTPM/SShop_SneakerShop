package com.example.sshop_sneakershop.Order.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop.Auth.views.SignInActivity
import com.example.sshop_sneakershop.Order.controllers.IOrderController
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityOrderListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderListActivity : AppCompatActivity(), IOrderListActivity, OrderClickListener {
    private lateinit var orderController: IOrderController

    private var orders: ArrayList<Order> = ArrayList()
    private var fullOrderList: ArrayList<Order> = ArrayList()

    private lateinit var binding: ActivityOrderListBinding
    private lateinit var orderAdapter: OrderAdapter

    override fun onStart() {
        super.onStart()

        val auth = Firebase.auth
        if (auth.currentUser == null || !auth.currentUser!!.isEmailVerified) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        orderController.onGetAllOrders()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        orderController = OrderController(this)
        val orderListActivity = this
        binding.orderListRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        orderAdapter = OrderAdapter(orders, orderListActivity, fullOrderList)
        binding.orderListRecyclerView.adapter = orderAdapter

        setSupportActionBar(binding.orderListToolbar)
        binding.orderListToolbar.setNavigationOnClickListener {
            finish()
        }

        orderController.onGetAllOrders()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAllOrder() {
        GlobalScope.launch(Dispatchers.Main) {
            orders.addAll(orderController.getAllOrder())

            withContext(Dispatchers.Main) {
                binding.orderListRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetAllOrdersSuccess(orders: ArrayList<Order>) {
        this.orders.clear()
        this.fullOrderList.clear()
        this.orders.addAll(orders)
        this.fullOrderList.addAll(orders)
        binding.orderListRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onGetAllOrdersFailed(message: String) {
        Log.i("error", message)
        MaterialAlertDialogBuilder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onCreateOrderSuccess(order: Order) {
        TODO("Not yet implemented")
    }

    override fun onCreateOrderFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onClick(order: Order) {
        val intent = Intent(applicationContext, OrderDetailActivity::class.java)
        intent.putExtra("item-id", order.id)
        Log.i("OrderListActivity", "Order id: ${order.id}")
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_with_search, menu)
        val searchItem = menu.findItem(R.id.search_icon)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                orderAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }
    override fun onResume() {
        super.onResume()
        orderController.onGetAllOrders()
        binding.orderListToolbar.collapseActionView()
    }
}