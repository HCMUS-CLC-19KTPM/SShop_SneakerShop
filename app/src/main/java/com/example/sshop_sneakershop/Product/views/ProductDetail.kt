package com.example.sshop_sneakershop.Product.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.Product.controllers.ProductController
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Review.Review
import com.example.sshop_sneakershop.Review.views.ReviewAdapter
import com.example.sshop_sneakershop.databinding.ActivityItemDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class ProductDetail : AppCompatActivity(), ItemClickListener, IProductView {
    private lateinit var binding: ActivityItemDetailBinding
    private lateinit var productList: List<Product>
    private lateinit var reviewList: List<Review>

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

        //Reviews
        //Review list initialization
        val myReview = Review(
            R.drawable.shoe, "Lewis Hamilton", 100, "19-04-2021 5:13PM", true, 3.5,
            "This is the review content. This line should be less than 200 words and has no special character."
        )
        reviewList = listOf(myReview, myReview, myReview, myReview, myReview)

        binding.recyclerViewReview.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ReviewAdapter(reviewList)
        }
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("itemID", product.id)
        startActivity(intent)
        finish()
    }

    private fun getProductDetail(id: String) {
        GlobalScope.launch {
            val product = productController.getProductById(id)

            withContext(Dispatchers.Main) {
                binding.pictureTitle.text = product.brand
                binding.categoryTag.text = product.category
                binding.productName.text = product.name
                val priceValue = product.price - 1.0
                binding.productPrice.text = "$$priceValue"
                val priceOldValue = product.price
                binding.productOldPrice.text = "$$priceOldValue"
                val ratingValue = 3.0
                binding.rating.text = "$ratingValue/5.00"
                binding.descriptionContent.text = product.description


                val formatter = SimpleDateFormat("dd-MM-yyyy")
                val releaseDate = formatter.format(product.releaseDate)
                binding.infoContent.text = "Orgin: ${product.origin}\nStyle: ${product.category}\nReleased date: ${releaseDate}"
            }
        }
    }
}