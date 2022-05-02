package com.example.sshop_sneakershop.Product.controllers

import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.models.ProductModel
import com.example.sshop_sneakershop.Product.views.IProductView
import com.example.sshop_sneakershop.Review.models.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductController(private val view: IProductView? = null) : IProductController {

    private val model = ProductModel()

    /**
     * Get all products
     */
    override suspend fun getAllProducts(): ArrayList<Product> {
        return model.getAllProducts()
    }

    /**
     * Get all products 2
     */
    override fun onGetAllProducts(limit: Long?) {
        CoroutineScope(Dispatchers.Main).launch {
            val products = if (limit != null) {
                model.getAllProducts(limit)
            } else {
                model.getAllProducts()
            }
            withContext(Dispatchers.Main) {
                view?.onShowAllProducts(products)
            }
        }
    }

    /**
     * Get product by id
     */
    override suspend fun getProductById(id: String): Product {
        return model.getProductById(id)
    }

    /**
     * Get product by id 2
     */
    override fun onGetProductById(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val product = model.getProductById(id)
            withContext(Dispatchers.Main) {
                view?.onShowProductDetail(product)
            }
        }
    }

    override fun onGetProductsByCategory(category: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val products = if (category == "All") model.getAllProducts() else model.getProductsByCategory(category)
            withContext(Dispatchers.Main) {
                view?.onShowProductsByCategory(products)
            }
        }
    }

    /**
     * Get product by category
     */
    override suspend fun getProductsByCategory(category: String): ArrayList<Product> {
        if (category == "all") {
            return model.getAllProducts()
        }

        return model.getProductsByCategory(category)
    }

    override suspend fun addReview(productID: String, userId: String, review: Review): Boolean {
        return model.addReview(productID, userId, review)
    }

}