package com.example.sshop_sneakershop.Account.models

data class Payment(
    val name: String,
    val type: String,
    val number: String,
    val since: String
) {

    constructor() : this("", "", "", "")
}