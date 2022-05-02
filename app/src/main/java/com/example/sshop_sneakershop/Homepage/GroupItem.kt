package com.example.sshop_sneakershop.Homepage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sshop_sneakershop.Product.controllers.ProductController
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.IProductActivity
import com.example.sshop_sneakershop.Product.views.ProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityGroupItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupItem : AppCompatActivity(), ItemClickListener, IProductActivity {
    private var productList: ArrayList<Product> = ArrayList()
    private var fullProductList: ArrayList<Product> = ArrayList()
    private lateinit var productController: ProductController
    private lateinit var productAdapter: ProductAdapter
    private lateinit var category: String
    private lateinit var binding: ActivityGroupItemBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productList = ArrayList()
        productController = ProductController(this)

        category = intent.getStringExtra("category-id").toString()
        binding.groupListToolbar.title = category

        val mainActivity = this
//        binding.productRecyclerView.apply {
//            layoutManager = GridLayoutManager(applicationContext, 2)
//            adapter = ProductAdapter(productList, mainActivity, productList)
//        }
        binding.productRecyclerView.layoutManager =  GridLayoutManager(applicationContext, 2)
        productAdapter = ProductAdapter(productList, mainActivity, fullProductList)
        binding.productRecyclerView.adapter = productAdapter

        setSupportActionBar(binding.groupListToolbar)
        binding.groupListToolbar.setNavigationOnClickListener {
            finish()
        }

        productController.onGetProductsByCategory(category)

        var stateLike = false
        var statePrice = false
        var stateTime = false

        binding.likeSortButton.setOnClickListener {
            stateLike = if (!stateLike) {
                productList.sortBy { it.rating }
                true
            } else {
                productList.sortByDescending { it.rating }
                false
            }
            binding.productRecyclerView.adapter?.notifyDataSetChanged()
        }

        binding.priceSortButton.setOnClickListener {
            statePrice = if (!statePrice) {
                productList.sortBy { it.price }
                true
            } else {
                productList.sortByDescending { it.price }
                false
            }
            binding.productRecyclerView.adapter?.notifyDataSetChanged()
        }

        binding.dateSortButton.setOnClickListener {
            stateTime = if (!stateTime) {
                productList.sortBy { it.releaseDate }
                true
            } else {
                productList.sortByDescending { it.releaseDate }
                false
            }
            binding.productRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun onClick(product: Product) {
        GlobalScope.launch(Dispatchers.Main) {
            productController.addViewedProduct(product)

            val intent = Intent(applicationContext, ProductDetail::class.java)
            intent.putExtra("item-id", product.id)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getProducts() {
        GlobalScope.launch(Dispatchers.Main) {
            productList.addAll(productController.getProductsByCategory(category))

            withContext(Dispatchers.Main) {
                binding.productRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
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
        binding.productRecyclerView.adapter?.notifyDataSetChanged()
    }
    override fun onCreateOptionsMenu(menu: Menu):Boolean {
        menuInflater.inflate(R.menu.top_app_bar_with_search, menu)
        val searchItem = menu.findItem(R.id.search_icon)
        Log.i("searchView", "go here 1")
        val searchView = searchItem.actionView as SearchView
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