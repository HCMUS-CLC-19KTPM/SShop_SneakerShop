package com.example.sshop_sneakershop.homepage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityGroupItemBinding

class GroupItem : AppCompatActivity(), ItemClickListener {
    private lateinit var category: String
    private lateinit var binding: ActivityGroupItemBinding
    private lateinit var productList: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("categoryID").toString()
        binding.title.text = category

        val myItem = Product("", 83.03, "Grand Court", R.drawable.shoe)
        productList = listOf(
            myItem,
            myItem,
            myItem,
            myItem,
            myItem,
            myItem,
            myItem,
            myItem,
            myItem,
            myItem,
            myItem,
            myItem
        )

        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = ItemAdapter(productList, mainActivity)
        }

    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ItemDetail::class.java)
        intent.putExtra("itemID", product.id)
        startActivity(intent)
    }
}