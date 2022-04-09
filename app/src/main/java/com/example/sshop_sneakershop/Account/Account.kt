package com.example.sshop_sneakershop.Account

data class Account(
    val id: String,
    val fullName: String?,
    val email: String,
    val address: String?,
    val phone: String?,
    val gender: String?,
    val dob: String?,
    val password: String,
    val avatar: String?,
    val status: Boolean,
    val payment: ArrayList<Payment>?
) {
    constructor() : this("", null, "", null, null, null, null, "", null, false, null)

    constructor(
        id: String,
        fullName: String,
        email: String,
        password: String,
        status: Boolean
    ) : this(id, fullName, email, null, null, null, null, password, null, status)

    constructor(
        id: String,
        fullName: String?,
        email: String,
        address: String?,
        phone: String?,
        gender: String?,
        dob: String?,
        password: String,
        avatar: String?,
        status: Boolean
    ) : this(id, fullName, email, address, phone, gender, dob, password, avatar, status, null)
}