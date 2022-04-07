package com.example.sshop_sneakershop.Product.controllers

import com.example.sshop_sneakershop.Product.Product

interface IProductController {
    suspend fun getAllProducts(): ArrayList<Product>
    suspend fun getProductById(id: String): Product
}