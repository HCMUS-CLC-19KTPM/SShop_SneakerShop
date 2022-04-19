package com.example.sshop_sneakershop.Cart.models

import com.example.sshop_sneakershop.Product.models.Product

class Cart(
    var id: String = "",
    var totalCost: Double = 0.0,
    var userId: String = "",
    var productList: ArrayList<Product> = ArrayList()
) {
    constructor() : this("", 0.0, "", ArrayList())

    constructor(
        totalCost: Double,
        userId: String,
        productList: ArrayList<Product>
    ) : this("", totalCost, userId, productList)

    fun calculateTotalCost() {
        totalCost = 0.0

        productList.forEach {
            totalCost += it.price * it.quantity
        }
    }
}