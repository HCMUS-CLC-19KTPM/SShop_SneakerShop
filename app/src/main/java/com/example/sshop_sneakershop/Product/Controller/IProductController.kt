package com.example.sshop_sneakershop.Product.Controller

import com.example.sshop_sneakershop.Product.Product

interface IProductController {
    suspend fun getAllProducts(): ArrayList<Product>
}