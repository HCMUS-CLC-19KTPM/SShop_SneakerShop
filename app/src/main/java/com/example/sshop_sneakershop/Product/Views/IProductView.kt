package com.example.sshop_sneakershop.Product.Views

import com.example.sshop_sneakershop.Product.Product

interface IProductView {
    fun showAllProducts(products: ArrayList<Product>)
}