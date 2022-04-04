package com.example.sshop_sneakershop.Product

import java.util.*

class Product {
    var id: String = ""
    var price: Double = 0.0
    var name: String = ""
    var image: String? = ""
    var description: String? = ""
    var quantity: Int = 0
    var category: String = ""
    var brand: String = ""
    var size: String = ""
    var origin: String = ""
    var releaseDate: Date? = null
    var rating: Double = 0.0

    constructor()

    constructor(id: String, price: Double, name: String, image: Int) {
        this.id = id
        this.price = price
        this.name = name
        this.image = image.toString()
    }

    constructor(id: String, price: Double, name: String, image: String, category: String) {
        this.id = id
        this.price = price
        this.name = name
        this.image = image
        this.category = category
    }

    constructor(
        id: String,
        price: Double,
        name: String,
        image: String?,
        description: String?,
        quantity: Int
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
        quantity: Int,
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
}