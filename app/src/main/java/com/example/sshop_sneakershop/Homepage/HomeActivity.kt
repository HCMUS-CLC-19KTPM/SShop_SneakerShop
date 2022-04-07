package com.example.sshop_sneakershop.Homepage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.sshop_sneakershop.Auth.views.AuthActivity
import com.example.sshop_sneakershop.Product.controllers.ProductController
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.Product.views.IProductView
import com.example.sshop_sneakershop.Product.views.ProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeActivity : AppCompatActivity(), ItemClickListener, IProductView {
    // creating object of ViewPager
    private var mViewPager: ViewPager? = null
    private var images = intArrayOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
    private var mViewPagerAdapter: BannerAdapter? = null
    private var isLoading = true

    //Item binder
    private lateinit var binding: ActivityHomeBinding

    private lateinit var productController: ProductController
    private val productList: ArrayList<Product> = ArrayList()

    override fun onStart() {
        super.onStart()

        val auth = Firebase.auth
        if (auth.currentUser == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productController = ProductController(this)

        //Item list initialization
        getAllProducts()

        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition { isLoading }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Banner - slider
        mViewPager = findViewById<View>(R.id.banner_view_pager) as ViewPager
        mViewPagerAdapter = BannerAdapter(this, images)
        mViewPager!!.adapter = mViewPagerAdapter

        val mainActivity = this
        binding.homeRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductAdapter(productList, mainActivity)
        }

        //Set categories listener
        val intent = Intent(this, GroupItem::class.java)
        binding.icon1.setOnClickListener {
            intent.putExtra("categoryID", "Plimsoll")
            startActivity(intent)
        }
        binding.icon2.setOnClickListener {
            intent.putExtra("categoryID", "High-Top")
            startActivity(intent)
        }
        binding.icon3.setOnClickListener {
            intent.putExtra("categoryID", "Athletic")
            startActivity(intent)
        }
        binding.icon4.setOnClickListener {
            intent.putExtra("categoryID", "Slip-on")
            startActivity(intent)
        }
        binding.icon5.setOnClickListener {
            intent.putExtra("categoryID", "Authentic")
            startActivity(intent)
        }
        binding.icon6.setOnClickListener {
            intent.putExtra("categoryID", "Leather")
            startActivity(intent)
        }
        binding.icon7.setOnClickListener {
            intent.putExtra("categoryID", "Canvas")
            startActivity(intent)
        }
        binding.icon8.setOnClickListener {
            intent.putExtra("categoryID", "Synthetic")
            startActivity(intent)
        }
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("item-id", product.id)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAllProducts() {
        //Load data
        GlobalScope.launch {
            try {
                productList.addAll(productController.getAllProducts())

                withContext(Dispatchers.Main) {
                    binding.homeRecyclerView.adapter?.notifyDataSetChanged()
                    Toast.makeText(
                        applicationContext,
                        productList.size.toString(),
                        Toast.LENGTH_LONG
                    )
                        .show()

                    isLoading = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}