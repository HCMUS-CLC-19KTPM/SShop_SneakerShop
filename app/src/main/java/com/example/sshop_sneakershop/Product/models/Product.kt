package com.example.sshop_sneakershop.Product.models

import com.example.sshop_sneakershop.Review.models.Review
import java.util.*
import kotlin.collections.ArrayList

object ProductInCart {
    var id: String = ""
    var image: String = ""
    var name: String = ""
    var price: Double = 0.0
    var quantity: Int = 0
    var size: String = ""
}

class Product {
    var id: String = ""
    var price: Double = 0.0
    var name: String? = null
    var image: String? = null
    var description: String? = null
    var quantity: ArrayList<Int>? = null
    var category: String? = null
    var brand: String? = null
    var size: String? = null
    var origin: String? = null
    var releaseDate: Date? = null
    var rating: Double = 0.0
    var reviews: ArrayList<Review>? = null

    constructor()

    constructor(id: String, price: Double, name: String, image: Int) {
        this.id = id
        this.price = price
        this.name = name
        this.image = image.toString()
    }

    constructor(id: String, price: Double, name: String, image: String, size: String) {
        this.id = id
        this.price = price
        this.name = name
        this.image = image
        this.size = size
    }

    constructor(
        id: String,
        price: Double,
        name: String,
        image: String?,
        description: String?,
        quantity: ArrayList<Int>
    ) {
        this.id = id
        this.price = price
        this.name = name
        this.image = image
        this.description = description
        this.quantity = quantity
    }

    constructor(
        id: String,
        price: Double,
        name: String,
        image: String?,
        description: String?,
        quantity: ArrayList<Int>,
        category: String,
        brand: String,
        size: String,
        origin: String,
        releaseDate: Date,
        rating: Double
    ) {
        this.id = id
        this.price = price
        this.name = name
        this.image = image
        this.description = description
        this.quantity = quantity
        this.category = category
        this.brand = brand
        this.size = size
        this.origin = origin
        this.releaseDate = releaseDate
        this.rating = rating
    }

    constructor(
        id: String,
        price: Double,
        name: String,
        image: String?,
        description: String?,
        quantity: ArrayList<Int>,
        category: String,
        brand: String,
        size: String,
        origin: String,
        releaseDate: Date,
        rating: Double,
        reviews: ArrayList<Review>?
    ) {
        this.id = id
        this.price = price
        this.name = name
        this.image = image
        this.description = description
        this.quantity = quantity
        this.category = category
        this.brand = brand
        this.size = size
        this.origin = origin
        this.releaseDate = releaseDate
        this.rating = rating
        this.reviews = reviews
    }
}