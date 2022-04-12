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

//        val myOrder = Order("Standard Express - VN2221342314234231K", "Nguyen Van A", "113 Nguyen Van Cu, District 5, HCM", "18001166", 1000.0, "22/2/2022", "1/3/2022")
//        orders = listOf(myOrder, myOrder, myOrder, myOrder, myOrder).toCollection(ArrayList())

        val adapter = OrderAdapter(orders)
        orderRecyclerView.adapter = adapter
        orderRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)
    }

    private fun getAllOrder() {
        GlobalScope.launch(Dispatchers.Main) {
            orders.addAll(orderController.getAllOrder())

            withContext(Dispatchers.Main) {
                orderRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }
}