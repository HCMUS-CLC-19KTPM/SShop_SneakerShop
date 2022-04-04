package com.example.sshop_sneakershop.Product.controllers

import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.Product.ProductModel
import com.example.sshop_sneakershop.Product.views.IProductView

class ProductController(private var view: IProductView) : IProductController {

    private val model = ProductModel()

    override suspend fun getAllProducts(): ArrayList<Product> {
        return model.getAllProducts()
    }

    override suspend fun getProductById(id: String): Product {
        return model.getProductById(id)
    }
}