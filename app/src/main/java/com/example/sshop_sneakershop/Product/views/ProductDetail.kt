package com.example.sshop_sneakershop.Product.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.controllers.ProductController
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Review.models.Review
import com.example.sshop_sneakershop.Review.views.ReviewAdapter
import com.example.sshop_sneakershop.databinding.ActivityItemDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.text.SimpleDateFormat

class ProductDetail : AppCompatActivity(), ItemClickListener, IProductView {
    private lateinit var binding: ActivityItemDetailBinding
    private lateinit var productList: List<Product>
    private val reviewList: ArrayList<Review> = ArrayList()

    private lateinit var productController: ProductController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productController = ProductController(this)

        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("item-id").toString()
        //Get item-data base on ID here
        Log.d("ID", id)
        getProductDetail(id)

        //Related products
        //Item list initialization
        val myItem = Product("", 83.03, "Grand Court", R.drawable.shoe)
        productList = listOf(myItem, myItem, myItem, myItem)

        val mainActivity = this
        binding.recyclerViewRelated.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductAdapter(productList, mainActivity)
        }

        binding.recyclerViewReview.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ReviewAdapter(reviewList)
        }
        binding.itemDetailToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("itemID", product.id)
        startActivity(intent)
        finish()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged", "SetTextI18n", "SimpleDateFormat")
    private fun getProductDetail(id: String) {
        GlobalScope.launch {
            val product = productController.getProductById(id)

            if (product.reviews != null) {
                reviewList.addAll(product.reviews!!)
            }

            withContext(Dispatchers.Main) {
                if (TextUtils.isEmpty(product.image)) {
                    binding.backgroundImageView.setImageResource(R.drawable.shoe)
                } else {
                    Picasso.get().load(product.image).into(binding.backgroundImageView)
                }

                binding.pictureTitle.text = product.brand
                binding.categoryTag.text = product.category
                binding.productName.text = product.name
                val priceValue = product.price - 1.0
                binding.productPrice.text = "$$priceValue"
                val priceOldValue = product.price
                binding.productOldPrice.text = "$$priceOldValue"
                val ratingValue = product.rating
                binding.rating.text = "$ratingValue/5.00"
                binding.descriptionContent.text = product.description


                val formatter = SimpleDateFormat("dd-MM-yyyy")
                val releaseDate = formatter.format(product.releaseDate!!)
                binding.infoContent.text =
                    "Origin: ${product.origin}\nStyle: ${product.category}\nReleased date: $releaseDate"

                binding.recyclerViewReview.adapter?.notifyDataSetChanged()
            }
        }
    }
}