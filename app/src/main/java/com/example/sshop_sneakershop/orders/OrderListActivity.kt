package com.example.sshop_sneakershop.orders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.checkout.ProductItem
import com.example.sshop_sneakershop.checkout.ProductItemAdapter

class OrderListActivity : AppCompatActivity() {
    lateinit var orders: ArrayList<Order>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        // Lookup the recyclerview in activity layout
        val orderRecyclerView = findViewById<RecyclerView>(R.id.order_list_recycler_view) as RecyclerView

        val myOrder = Order("Standard Express - VN2221342314234231K", "Nguyen Van A", "113 Nguyen Van Cu, District 5, HCM", "18001166", 1000.0, "22/2/2022", "1/3/2022")

        orders = listOf(myOrder, myOrder, myOrder, myOrder, myOrder).toCollection(ArrayList())

        val adapter = OrderAdapter(orders)

        orderRecyclerView.adapter = adapter

        orderRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)
    }
}