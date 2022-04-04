package com.example.sshop_sneakershop.Order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.Product.views.ProductItemAdapter
import com.example.sshop_sneakershop.Review.ReviewBottomSheetDialog

class OrderDetailActivity : AppCompatActivity() {
    lateinit var products: ArrayList<Product>
    var confirmBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val productRecyclerView = findViewById<RecyclerView>(R.id.order_detail_recycler_view)
        confirmBtn = findViewById(R.id.order_detail_button_confirm)

        val myProduct = Product("",100.0, "Shoe", "image url", "Description", 2)
        products = listOf(myProduct, myProduct, myProduct,myProduct, myProduct, myProduct,myProduct, myProduct, myProduct).toCollection(ArrayList())

        val adapter = ProductItemAdapter(products)

        productRecyclerView.adapter = adapter

        productRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)

        confirmBtn?.setOnClickListener {
            var reviewDialog: ReviewBottomSheetDialog = ReviewBottomSheetDialog()
            reviewDialog.show(supportFragmentManager, "Review")
        }
    }
}