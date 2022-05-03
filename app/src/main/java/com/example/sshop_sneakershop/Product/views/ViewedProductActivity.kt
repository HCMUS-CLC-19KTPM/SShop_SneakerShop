package com.example.sshop_sneakershop.Product.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.controllers.ProductController
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityViewedProductBinding

class ViewedProductActivity : AppCompatActivity(), ItemClickListener, IViewedProductActivity, IProductActivity {
    private lateinit var productController: ProductController
    private lateinit var productAdapter: ProductAdapter

    private lateinit var binding: ActivityViewedProductBinding

    private lateinit var category: String
    private val productList: ArrayList<Product> = ArrayList()
    private val fullProductList: ArrayList<Product> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        productController = ProductController(this as IViewedProductActivity)

        super.onCreate(savedInstanceState)
        binding = ActivityViewedProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainActivity = this
        binding.viewedProductRecyclerView.layoutManager =  GridLayoutManager(applicationContext, 2)
        productAdapter = ProductAdapter(productList, mainActivity, fullProductList)
        binding.viewedProductRecyclerView.adapter = productAdapter

        setSupportActionBar(binding.viewedProductListToolbar)
        binding.viewedProductListToolbar.setNavigationOnClickListener {
            finish()
        }

        var stateLike = false
        var statePrice = false
        var stateTime = false

        binding.viewedProductLikeSortButton.setOnClickListener {
            stateLike = if (!stateLike) {
                productList.sortBy { it.rating }
                true
            } else {
                productList.sortByDescending { it.rating }
                false
            }
            binding.viewedProductRecyclerView.adapter?.notifyDataSetChanged()
        }

        binding.viewedProductPriceSortButton.setOnClickListener {
            statePrice = if (!statePrice) {
                productList.sortBy { it.price }
                true
            } else {
                productList.sortByDescending { it.price }
                false
            }
            binding.viewedProductRecyclerView.adapter?.notifyDataSetChanged()
        }

        binding.viewedProductDateSortButton.setOnClickListener {
            stateTime = if (!stateTime) {
                productList.sortBy { it.releaseDate }
                true
            } else {
                productList.sortByDescending { it.releaseDate }
                false
            }
            binding.viewedProductRecyclerView.adapter?.notifyDataSetChanged()
        }

        productController.onGetViewedProducts()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onAddViewedProductsSuccess(products: ArrayList<Product>) {
        productList.clear()
        fullProductList.clear()
        productList.addAll(products)
        fullProductList.addAll(products)
        binding.viewedProductRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onAddViewedProductsFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("item-id", product.id)
        startActivity(intent)
    }

    override fun onShowAllProducts(products: ArrayList<Product>) {
        TODO("Not yet implemented")
    }

    override fun onShowProductDetail(product: Product) {
        TODO("Not yet implemented")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onShowProductsByCategory(products: ArrayList<Product>) {
        productList.clear()
        fullProductList.clear()
        productList.addAll(products)
        fullProductList.addAll(products)
        binding.viewedProductRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_with_search, menu)
        val searchItem = menu?.findItem(R.id.search_icon)
        Log.i("searchView", "go here 1")
        val searchView = searchItem?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("searchView", "go here 2")
                productAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }
}