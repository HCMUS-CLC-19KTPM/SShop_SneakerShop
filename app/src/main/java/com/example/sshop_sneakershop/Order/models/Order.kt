package com.example.sshop_sneakershop.Order.models

import java.util.*

class Order (
    var id: String,
    var name: String,
    var address: String,
    var phone: String,
    var totalCost: Double,
    var startDate: Date,
    var endDate: Date
) {
    constructor() : this("", "", "", "", 0.0, Date(), Date())
}