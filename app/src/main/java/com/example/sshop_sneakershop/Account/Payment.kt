package com.example.sshop_sneakershop.Account

data class Payment(
    val name: String,
    val type: String,
    val number: String,
    val since: String
) {

    constructor() : this("", "", "", "")
}