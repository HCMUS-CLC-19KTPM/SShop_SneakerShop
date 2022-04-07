package com.example.sshop_sneakershop.Account

data class Account(
    var id: String,
    var fullName: String?,
    var email: String,
    var address: String?,
    var phone: String?,
    var gender: String?,
    var dob: String?,
    var password: String,
    var avatar: String?,
    var status: Boolean,
) {
    constructor() : this("", null, "", null, null, null, null, "", null, false)

    constructor(id: String,
                fullName: String,
                email: String,
                password: String,
                status: Boolean) : this(id, fullName, email, null, null, null, null, password, null, status)
}