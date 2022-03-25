package com.example.sshop_sneakershop.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityItemDetailBinding
import java.time.LocalDate
import java.util.*

class ItemDetail : AppCompatActivity(), ItemClickListener {
    private lateinit var binding: ActivityItemDetailBinding
    private lateinit var itemList: List<Item>
    private lateinit var reviewList: List<Review>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("itemID").toString()
        //Get item-data base on ID here
        Log.d("ID",id)
        //Sample data
        binding.pictureTitle.text="Limited"
        binding.categoryTag.text="Plimsoll"
        binding.productName.text="Grand Court Adidas"
        val priceValue: Double = 20.05
        binding.productPrice.text = "$$priceValue"
        val priceOldValue: Double = 21.0
        binding.productOldPrice.text = "$$priceOldValue"
        val ratingValue: Double = 3.0
        binding.rating.text="$ratingValue/5.00"
        binding.descriptionContent.text="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."

        binding.infoContent.text="Orgin: US\nStyle: Street Sneaker\nReleased date: 19-04-2021"

        //Related products
        //Item list initialization
        val myItem = Item(83.03, "Grand Court", R.drawable.shoe)
        itemList = listOf(myItem, myItem, myItem, myItem)

        val mainActivity = this
        binding.recyclerViewRelated.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ItemAdapter(itemList, mainActivity)
        }

        //Reviews
        //Review list initialization
        val myReview = Review(R.drawable.shoe,"Lewis Hamilton",100, "19-04-2021 5:13PM",true,3.5,
        "This is the review content. This line should be less than 200 words and has no special character.")
        reviewList = listOf(myReview,myReview,myReview,myReview,myReview)

        binding.recyclerViewReview.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ReviewAdapter(reviewList)
        }


    }
    override fun onClick(item: Item) {
        val intent = Intent(applicationContext, ItemDetail::class.java)
        intent.putExtra("itemID", item.id)
        startActivity(intent)
        finish()
    }
}