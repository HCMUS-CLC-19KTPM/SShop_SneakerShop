package com.example.sshop_sneakershop.Product.controllers

import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.models.ProductModel
import com.example.sshop_sneakershop.Product.views.IProductActivity
import com.example.sshop_sneakershop.Product.views.IViewedProductActivity
import com.example.sshop_sneakershop.Review.models.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductController : IProductController {
    private val model = ProductModel()
    private var productActivity: IProductActivity? = null
    private var viewedProductActivity: IViewedProductActivity? = null

    constructor()

    constructor(activity: IProductActivity? = null) {
        this.productActivity = activity
    }

    constructor(activity: IViewedProductActivity? = null) {
        this.viewedProductActivity = activity
    }

    /**
     * Get all products
     */
    override suspend fun getAllProducts(): ArrayList<Product> {
        return model.getAllProducts()
    }

    override suspend fun addReview(productID: String, userId: String, review: Review): Boolean {
        return model.addReview(productID, userId, review)
    }

    /**
     * Add viewed product
     */
    override suspend fun addViewedProduct(product: Product) {
        model.addViewedProduct(product)
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
                productActivity?.onShowAllProducts(products)
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
                productActivity?.onShowProductDetail(product)
            }
        }
    }

    override fun onGetProductsByCategory(category: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val products = if (category == "All") model.getAllProducts() else model.getProductsByCategory(category)
            withContext(Dispatchers.Main) {
                productActivity?.onShowProductsByCategory(products)
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
}