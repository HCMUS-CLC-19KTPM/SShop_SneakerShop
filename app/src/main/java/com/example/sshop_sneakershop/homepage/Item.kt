package com.example.sshop_sneakershop.homepage

var itemList = mutableListOf<Item>()

class Item(
    var price: Double,
    var name: String,
    var cover: Int,
    val id: Int? = itemList.size
)