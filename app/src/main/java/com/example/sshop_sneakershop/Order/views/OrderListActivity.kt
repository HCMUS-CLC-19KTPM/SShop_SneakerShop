package com.example.sshop_sneakershop.Order.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderListActivity : AppCompatActivity() {

    private lateinit var orderRecyclerView: RecyclerView

    private val orderController = OrderController()
    private var orders: ArrayList<Order> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        getAllOrder()

        // Lookup the recyclerview in activity layout
        orderRecyclerView = findViewById(R.id.order_list_recycler_view)

        val adapter = OrderAdapter(orders)
        orderRecyclerView.adapter = adapter
        orderRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)
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
}