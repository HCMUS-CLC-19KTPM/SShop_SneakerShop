package com.example.sshop_sneakershop.Order.views

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop.Auth.views.SignInActivity
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Order.controllers.IOrderController
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.CustomProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.Review.views.ReviewListProduct
import com.example.sshop_sneakershop.databinding.ActivityOrderDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class OrderDetailActivity : AppCompatActivity(), IOrderDetailActivity, ItemClickListener {
    private lateinit var orderController: IOrderController

    private val products: ArrayList<Product> = ArrayList()
    private var order: Order = Order()

    private lateinit var binding: ActivityOrderDetailBinding

    override fun onStart() {
        super.onStart()

        val auth = Firebase.auth
        if (auth.currentUser == null || !auth.currentUser!!.isEmailVerified) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderController = OrderController(this)
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
        binding.orderDetailButtonReport.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Success")
                .setMessage("Report successfully. Admin will contact to support you as soon as possible.")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                .show()
        }

        binding.orderDetailButtonConfirm.setOnClickListener {
            //IMPLEMENTS Update order status
            //Move to review
            if (order.deliveryStatus == "On Delivery"){
                MaterialAlertDialogBuilder(this)
                    .setTitle("Warning")
                    .setMessage("Your order has not been completed. Please wait for the admin to confirm completion to proceed with the review.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else if(order.deliveryStatus == "Cancelled"){
                MaterialAlertDialogBuilder(this)
                    .setTitle("Warning")
                    .setMessage("Your order has been cancelled. Please contact admin to get the reason.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else{
                val intent = Intent(this, ReviewListProduct::class.java)
                intent.putExtra("order-id", orderID)
                startActivity(intent)
            }
        }
    }

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

        // Update update product recycler view
        products.clear()
        products.addAll(this.order.cart)
        binding.orderDetailRecyclerView.adapter?.notifyDataSetChanged()

        // Check if this order is delivered successfully
//        binding.orderDetailButtonConfirm.isEnabled = order.deliveryStatus == "Completed"
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