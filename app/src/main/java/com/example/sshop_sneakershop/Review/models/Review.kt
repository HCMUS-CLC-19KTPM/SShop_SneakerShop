package com.example.sshop_sneakershop.Review.models

import com.example.sshop_sneakershop.Account.models.Account
import java.util.*

class Review(
    var id: String,
    var user: Account,
    var date: Date,
    var rate: Double, //Int vale scale from 0-5
    var content: String,
) {
    constructor() : this(
        "",
        Account(),
        Date(),
        0.0,
        ""
    )
}