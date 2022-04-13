package com.example.sshop_sneakershop.Account.models

data class Account(
    var id: String,
    val fullName: String?,
    val email: String,
    val address: String?,
    val phone: String?,
    val gender: String?,
    val dob: String?,
    val avatar: String?,
    val status: Boolean,
    val payment: ArrayList<Payment>?,
    val numOfReview: Int = 0
) {
    constructor() : this("", null, "", null, null, null, null, null, true, null)

    constructor(
        id: String,
        fullName: String,
        email: String,
        status: Boolean
    ) : this(id, fullName, email, null, null, null, null, null, status, null)

    constructor(email: String) : this("", null, email, null, null, null, null, null, true, null)

    constructor(
        id: String,
        fullName: String,
        email: String,
        avatar: String?,
    ) : this(id, fullName, email, null, null, null, null, avatar, true, null)

    constructor(
        id: String,
        fullName: String?,
        email: String,
        address: String?,
        phone: String?,
        gender: String?,
        dob: String?,
        avatar: String?,
        status: Boolean
    ) : this(id, fullName, email, address, phone, gender, dob, avatar, status, null)
}