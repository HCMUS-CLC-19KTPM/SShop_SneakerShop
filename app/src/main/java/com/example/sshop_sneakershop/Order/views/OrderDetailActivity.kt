package com.example.sshop_sneakershop.Order.views

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductItemAdapter
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Review.views.ReviewBottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var orderController: OrderController
    private val products: ArrayList<Product> = ArrayList()

    private lateinit var estimateDateTextView: TextView
    private lateinit var deliveryDescriptionTextView: TextView
    private lateinit var startDateTextView: TextView
    private lateinit var endDateTextView: TextView
    private lateinit var customerName: TextView
    private lateinit var customerAddress: TextView
    private lateinit var customerPhone: TextView
    private lateinit var totalCost: TextView
    private lateinit var confirmBtn: Button
    private lateinit var productRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        orderController = OrderController()
        getOrder()

        productRecyclerView = findViewById(R.id.order_detail_recycler_view)

        estimateDateTextView = findViewById(R.id.order_detail_textview_estimate_date_1)
        deliveryDescriptionTextView = findViewById(R.id.order_detail_textview_delivery_description)
        startDateTextView = findViewById(R.id.order_detail_textview_start_date)
        endDateTextView = findViewById(R.id.order_detail_textview_estimated_date_2)
        customerName = findViewById(R.id.order_detail_textview_customer_name)
        customerAddress = findViewById(R.id.order_detail_textview_customer_address)
        customerPhone = findViewById(R.id.order_detail_textview_customer_phone)
        totalCost = findViewById(R.id.order_detail_textview_total_cost)
        confirmBtn = findViewById(R.id.order_detail_button_confirm)

        val adapter = ProductItemAdapter(products)

        productRecyclerView.adapter = adapter

        productRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)

        confirmBtn.setOnClickListener {
            val reviewDialog = ReviewBottomSheetDialog()
            reviewDialog.show(supportFragmentManager, "Review")
        }
    }

    private fun getOrder() {
        GlobalScope.launch(Dispatchers.Main) {
            val formatter = SimpleDateFormat("dd/MM/yyyy")

            val order = orderController.getOrderById("yy3U8c8oYyejCrhcXHJR")
            products.addAll(order.productInCart)

            withContext(Dispatchers.Main) {
                estimateDateTextView.text = formatter.format(order.endDate)
                deliveryDescriptionTextView.text = order.deliveryStatus
                startDateTextView.text = formatter.format(order.startDate)
                endDateTextView.text = formatter.format(order.endDate)
                customerName.text = order.name
                customerAddress.text = order.address
                customerPhone.text = order.phone
                totalCost.text = order.totalCost.toString()
                productRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }
}