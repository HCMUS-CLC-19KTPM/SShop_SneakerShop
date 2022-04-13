package com.example.sshop_sneakershop.Cart.models

import com.example.sshop_sneakershop.Product.models.ProductInCart

class Cart(
    var id: String = "",
    var totalCost: Double = 0.0,
    var userId: String = "",
    var productList: ArrayList<ProductInCart>? = null
) {


    constructor() : this("", 0.0, "", null)
    constructor(
        totalCost: Double,
        userId: String,
        productList: ArrayList<ProductInCart>?
    ) : this("", totalCost, userId, productList)
}