package com.example.sshop_sneakershop.Product.controllers

import com.example.sshop_sneakershop.Product.models.Product

interface IProductController {
    suspend fun getAllProducts(): ArrayList<Product>
    suspend fun getProductById(id: String): Product
    suspend fun getProductsByCategory(category: String): ArrayList<Product>
    fun onGetAllProducts()
    fun onGetProductById(id: String)
    fun onGetProductsByCategory(category: String)
}