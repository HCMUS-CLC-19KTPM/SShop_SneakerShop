package com.example.sshop_sneakershop.Review.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Order.views.IOrderDetailActivity
import com.example.sshop_sneakershop.Order.views.OrderClickListener
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.CustomProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityOrderDetailBinding
import com.example.sshop_sneakershop.databinding.ActivityReviewListProductBinding

class ReviewListProduct : AppCompatActivity(), ItemClickListener {
    private val products: ArrayList<Product> = ArrayList()
    private lateinit var binding: ActivityReviewListProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set up recycler view
        val reviewActivity = this
        binding.reviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = CustomProductAdapter(products, reviewActivity)
        }

        //Back button
        binding.reviewToolbar.setNavigationOnClickListener {
            finish()
        }

    }
    override fun onClick(product: Product) {
        //Start review for single product
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("item-id", product.id)
        startActivity(intent)
    }
}