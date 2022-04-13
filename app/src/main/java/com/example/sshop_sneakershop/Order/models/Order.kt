package com.example.sshop_sneakershop.Order.models

import com.example.sshop_sneakershop.Product.models.ProductInCart
import java.util.*
import kotlin.collections.ArrayList

data class Order(
    var id: String,
    var name: String,
    var phone: String,
    var address: String,
    var productInCart: ArrayList<ProductInCart>,
    var deliveryStatus: String,
    var orderedDate: Date,
    var startDate: Date,
    var endDate: Date,
    var shippingFee: Double,
    var totalCost: Double,
    var user: String
) {
    constructor() : this("", "", "", "", 0.0, Date(), Date())


    constructor(
        id: String,
        name: String,
        address: String,
        phone: String,
        totalCost: Double,
        startDate: Date,
        endDate: Date
    ) : this(
        id, name, phone, address,
        ArrayList(), "", Date(), startDate, endDate, 0.0, totalCost, ""
    )
}