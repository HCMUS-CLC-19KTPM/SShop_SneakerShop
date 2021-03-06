package com.example.sshop_sneakershop.Product.views

import com.example.sshop_sneakershop.Product.models.Product

interface IProductActivity {
    fun onShowAllProducts(products: ArrayList<Product>)
    fun onShowProductDetail(product: Product)
    fun onShowProductsByCategory(products: ArrayList<Product>)
}