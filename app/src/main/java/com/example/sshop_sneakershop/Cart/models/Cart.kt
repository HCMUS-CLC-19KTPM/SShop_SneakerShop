package com.example.sshop_sneakershop.Cart.models

class Cart {
    var id: String = ""
    var price: Double = 0.0
    var name: String? = null
    var image: String? = null
    var quantity: Int = 0
    var size: String? = null
    constructor()

    constructor(id: String, price: Double, name: String, image: String, quantity: Int, size: String) {
        this.id = id
        this.price = price
        this.name = name
        this.image = image.toString()
        this.quantity = quantity
        this.size = "Size: $size"
    }
}