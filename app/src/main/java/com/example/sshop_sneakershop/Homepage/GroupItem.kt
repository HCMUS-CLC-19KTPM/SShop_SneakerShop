package com.example.sshop_sneakershop.Homepage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
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
        binding.groupListToolbar.title = category

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
        binding.productRecyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = ProductAdapter(productList, mainActivity)
        }

        binding.groupListToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("itemID", product.id)
        startActivity(intent)
    }
}