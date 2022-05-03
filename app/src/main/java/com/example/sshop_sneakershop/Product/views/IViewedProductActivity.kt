package com.example.sshop_sneakershop.Product.views

import com.example.sshop_sneakershop.Product.models.Product

interface IViewedProductActivity {
    fun onAddViewedProductsSuccess(products: ArrayList<Product>)
    fun onAddViewedProductsFailed(message: String)
}