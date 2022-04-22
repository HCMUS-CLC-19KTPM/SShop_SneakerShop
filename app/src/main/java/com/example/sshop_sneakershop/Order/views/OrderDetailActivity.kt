package com.example.sshop_sneakershop.Order.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductItemAdapter
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Review.views.ReviewBottomSheetDialog
import com.example.sshop_sneakershop.databinding.ActivityOrderDetailBinding
import com.example.sshop_sneakershop.databinding.ActivityUserBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat

class OrderDetailActivity : AppCompatActivity(), IOrderDetailActivity {

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

    private lateinit var binding: ActivityOrderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderController = OrderController(orderDetailActivity = this)

        estimateDateTextView = binding.orderDetailTextviewEstimateDate1
        deliveryDescriptionTextView = binding.orderDetailTextviewDeliveryDescription
        startDateTextView = binding.orderDetailTextviewStartDate
        endDateTextView = binding.orderDetailTextviewEstimatedDate2
        customerName = binding.orderDetailTextviewCustomerName
        customerAddress = binding.orderDetailTextviewCustomerAddress
        customerPhone = binding.orderDetailTextviewCustomerPhone
        totalCost = binding.orderDetailTextviewTotalCost
        confirmBtn = binding.orderDetailButtonConfirm

        productRecyclerView = binding.orderDetailRecyclerView
        productRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrderDetailActivity) //GridLayoutManager(this, 2)
            adapter = ProductItemAdapter(products)
        }

        confirmBtn.setOnClickListener {
            val reviewDialog = ReviewBottomSheetDialog()
            reviewDialog.show(supportFragmentManager, "Review")
        }

        getOrder()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged", "SimpleDateFormat")
    private fun getOrder() {
        GlobalScope.launch(Dispatchers.Main) {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val order = orderController.getOrderById("yy3U8c8oYyejCrhcXHJR")
            if (order != null) {
                products.addAll(order.cart)
            }

            withContext(Dispatchers.Main) {
                if (order != null) {
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

    @SuppressLint("SimpleDateFormat", "NotifyDataSetChanged")
    override fun onGetOrderByIdSuccess(order: Order) {
        products.addAll(order.cart)

        val formatter = SimpleDateFormat("dd/MM/yyyy")
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

    override fun onGetOrderByIdFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}