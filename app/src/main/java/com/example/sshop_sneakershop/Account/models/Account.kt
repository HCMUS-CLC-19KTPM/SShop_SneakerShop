package com.example.sshop_sneakershop.Account.models

import com.google.firebase.firestore.Exclude

class Account(
    var id: String,
    var fullName: String?,
    var email: String,
    var address: String?,
    var phone: String?,
    var gender: String?,
    var dob: String?,
    var avatar: String?,
    var status: Boolean,
    var payments: ArrayList<Payment>?,
    var numOfReview: Int = 0
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

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "fullName" to fullName,
            "email" to email,
            "address" to address,
            "phone" to phone,
            "gender" to gender,
            "dob" to dob
        )
    }
}