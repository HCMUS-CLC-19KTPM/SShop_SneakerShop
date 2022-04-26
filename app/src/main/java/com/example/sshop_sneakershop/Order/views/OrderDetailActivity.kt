package com.example.sshop_sneakershop.Order.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Auth.views.AuthActivity
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.CustomProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Review.views.ReviewBottomSheetDialog
import com.example.sshop_sneakershop.databinding.ActivityOrderDetailBinding
import com.example.sshop_sneakershop.databinding.ActivityUserBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class OrderDetailActivity : AppCompatActivity(), IOrderDetailActivity, ItemClickListener {

    private lateinit var orderController: OrderController
    private val products: ArrayList<Product> = ArrayList()
    private var order: Order = Order()
    private lateinit var binding: ActivityOrderDetailBinding

    override fun onStart() {
        super.onStart()

        val auth = Firebase.auth
        if (auth.currentUser == null || !auth.currentUser!!.isEmailVerified) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        orderController = OrderController(null, this)
        val orderID = intent.getStringExtra("item-id").toString()
        orderController.onGetOrderById(orderID)

        val orderDetailActivity = this
        binding.orderDetailRecyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = CustomProductAdapter(products, orderDetailActivity)
        }
        binding.orderDetailToolbar.setNavigationOnClickListener {
            finish()
        }
    }

//    @OptIn(DelicateCoroutinesApi::class)
//    @SuppressLint("NotifyDataSetChanged", "SimpleDateFormat")
//    private fun getOrder() {
//        GlobalScope.launch(Dispatchers.Main) {
//            val formatter = SimpleDateFormat("dd/MM/yyyy")
//            val order = orderController.getOrderById("yy3U8c8oYyejCrhcXHJR")
//            if (order != null) {
//                products.addAll(order.cart)
//            }
//
//            withContext(Dispatchers.Main) {
//                if (order != null) {
//                    estimateDateTextView.text = formatter.format(order.endDate)
//                    deliveryDescriptionTextView.text = order.deliveryStatus
//                    startDateTextView.text = formatter.format(order.startDate)
//                    endDateTextView.text = formatter.format(order.endDate)
//                    customerName.text = order.name
//                    customerAddress.text = order.address
//                    customerPhone.text = order.phone
//                    totalCost.text = order.totalCost.toString()
//                    productRecyclerView.adapter?.notifyDataSetChanged()
//                }
//            }
//        }
//    }

    @SuppressLint("SimpleDateFormat", "NotifyDataSetChanged", "SetTextI18n")
    override fun onGetOrderByIdSuccess(order: Order) {
        this.order = order
        binding.orderDetailTextviewDeliveryDescription.text =  "Standard Express - " + this.order.id
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val startDate = formatter.format(this.order.startDate)
        binding.orderDetailTextviewStartDate.text = startDate
        val endDate = formatter.format(this.order.endDate)
        binding.orderDetailTextviewEstimateDate1.text = endDate
        binding.orderDetailTextviewEstimatedDate2.text = endDate
        binding.orderDetailTextviewCustomerName.text = this.order.name
        binding.orderDetailTextviewCustomerAddress.text = this.order.address
        binding.orderDetailTextviewCustomerPhone.text = this.order.phone
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        binding.orderDetailTextviewTotalCost.text = "$${df.format(this.order.totalCost)}"
        binding.orderDetailTextviewStatus.text = this.order.deliveryStatus
        products.clear()
        products.addAll(this.order.cart)
        binding.orderDetailRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onGetOrderByIdFailed(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("item-id", product.id)
        startActivity(intent)
    }
}