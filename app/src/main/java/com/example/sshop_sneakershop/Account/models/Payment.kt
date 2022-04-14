package com.example.sshop_sneakershop.Account.models

import java.util.*

data class Payment(
    val name: String,
    val type: String,
    val number: String,
    val since: Date,
) {

    constructor() : this("", "", "", Date())
}