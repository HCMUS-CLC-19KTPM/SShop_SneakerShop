package com.example.sshop_sneakershop.Homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivitySearchBinding

class Search : AppCompatActivity(), ItemClickListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var productList: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text = "Result for 'Very chonk'"

        val myItem = Product("", 83.03,"Grand Court",R.drawable.shoe)
        productList = arrayListOf(myItem,myItem,myItem,myItem,myItem,myItem,myItem,myItem
            ,myItem,myItem,myItem,myItem)

        val mainActivity = this
        binding.searchRecyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = ProductAdapter(productList,mainActivity, productList)
        }

    }
    override fun onClick(product: Product)
    {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("itemID", product.id)
        startActivity(intent)
    }
}