package com.example.sshop_sneakershop.Product.Controller

import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.Product.ProductModel
import com.example.sshop_sneakershop.Product.Views.IProductView

class ProductController(private var view: IProductView) : IProductController {

    private val model = ProductModel()

    override suspend fun getAllProducts(): ArrayList<Product> {
        return model.getAllProducts()
    }
}