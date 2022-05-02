package com.example.sshop_sneakershop.Product.controllers

import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Review.models.Review

interface IProductController {
    suspend fun getAllProducts(): ArrayList<Product>
    suspend fun getProductById(id: String): Product
    suspend fun getProductsByCategory(category: String): ArrayList<Product>
    suspend fun addReview(productID: String, userId: String, review: Review): Boolean
    fun onGetAllProducts(limit: Long? = null)
    fun onGetProductById(id: String)
    fun onGetProductsByCategory(category: String)
}