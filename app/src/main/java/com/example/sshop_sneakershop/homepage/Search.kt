package com.example.sshop_sneakershop.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityGroupItemBinding
import com.example.sshop_sneakershop.databinding.ActivitySearchBinding

class Search : AppCompatActivity(), ItemClickListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var itemList: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text = "Result for 'Very chonk'"

        val myItem = Item(83.03,"Grand Court",R.drawable.shoe)
        itemList = listOf(myItem,myItem,myItem,myItem,myItem,myItem,myItem,myItem
            ,myItem,myItem,myItem,myItem)

        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = ItemAdapter(itemList,mainActivity)
        }

    }
    override fun onClick(item: Item)
    {
        val intent = Intent(applicationContext, ItemDetail::class.java)
        intent.putExtra("itemID", item.id)
        startActivity(intent)
    }
}