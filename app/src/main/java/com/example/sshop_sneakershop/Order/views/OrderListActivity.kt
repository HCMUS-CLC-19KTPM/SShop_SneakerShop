package com.example.sshop_sneakershop.Order.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.views.AccountActivity
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.R
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderListActivity : AppCompatActivity(), IOrderListActivity {
    private lateinit var orderController: OrderController

    private var orders: ArrayList<Order> = ArrayList()

    private lateinit var orderRecyclerView: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        // Lookup the recyclerview in activity layout
        orderRecyclerView = findViewById(R.id.order_list_recycler_view)

        val adapter = OrderAdapter(orders)
        orderRecyclerView.adapter = adapter
        orderRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)

        findViewById<MaterialToolbar>(R.id.order_list_toolbar).setNavigationOnClickListener {
            finish()
        }
        orderController = OrderController()
        getAllOrder()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAllOrder() {
        GlobalScope.launch(Dispatchers.Main) {
            orders.addAll(orderController.getAllOrder())

            withContext(Dispatchers.Main) {
                orderRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetAllOrdersSuccess(orders: ArrayList<Order>) {
        orders.clear()
        orders.addAll(orders)
        orderRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onGetAllOrdersFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateOrderSuccess(order: Order) {
        TODO("Not yet implemented")
    }

    override fun onCreateOrderFailed(message: String) {
        TODO("Not yet implemented")
    }
}