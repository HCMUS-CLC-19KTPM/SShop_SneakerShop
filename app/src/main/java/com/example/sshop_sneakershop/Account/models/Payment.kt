package com.example.sshop_sneakershop.Account.models

import java.util.*

data class Payment(
    var name: String,
    var type: String,
    var number: String,
    var since: Date
) {
    constructor() : this("", "", "", Date())
}