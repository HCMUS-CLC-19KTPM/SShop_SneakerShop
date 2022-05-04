package com.example.sshop_sneakershop.Homepage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.sshop_sneakershop.Account.views.AccountActivity
import com.example.sshop_sneakershop.Auth.views.SignInActivity
import com.example.sshop_sneakershop.Cart.CartActivity
import com.example.sshop_sneakershop.Order.views.OrderListActivity
import com.example.sshop_sneakershop.Product.controllers.ProductController
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.IProductActivity
import com.example.sshop_sneakershop.Product.views.ProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.Product.views.ViewedProductActivity
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityNavigationBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*


class Home : AppCompatActivity(), ItemClickListener,
    NavigationView.OnNavigationItemSelectedListener, IProductActivity {
    //ViewPager
    private var mViewPager: ViewPager? = null
    private var images = intArrayOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
    private var mViewPagerAdapter: BannerAdapter? = null

    //Adapter
    private lateinit var productController: ProductController
    private var productList: ArrayList<Product> = ArrayList()

    private lateinit var bindings: ActivityNavigationBinding


    override fun onStart() {
        super.onStart()
        val auth = Firebase.auth
        if (auth.currentUser == null || !auth.currentUser!!.isEmailVerified) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val checkedItem = SharedPreferences(this).darkMode
        if (checkedItem == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        productController = ProductController(this)

        bindings = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        setSupportActionBar(bindings.appBarNavigation.toolbar)
        supportActionBar?.title = "Home"

        val navView: NavigationView = bindings.navView
        navView.setNavigationItemSelectedListener(this)
        val toolbar: androidx.appcompat.widget.Toolbar = bindings.appBarNavigation.toolbar
        val drawerLayout: DrawerLayout = bindings.drawerLayout
        val openState = 1
        val closeState = 0
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, openState, closeState)
        //drawerLayout.setDrawerListener(toggle)
        //Get binding inside content
        val binding = bindings.appBarNavigation.contentHome

        //Banner - slider
        mViewPager = binding.bannerViewPager
        mViewPagerAdapter = BannerAdapter(this, images)
        mViewPager!!.adapter = mViewPagerAdapter

        //Set categories listener
        val intent = Intent(this, GroupItem::class.java)

        binding.viewAll.setOnClickListener {
            intent.putExtra("category-id", "All")
            startActivity(intent)
        }
        binding.icon1.setOnClickListener {
            intent.putExtra("category-id", "Plimsoll")
            startActivity(intent)
        }
        binding.icon2.setOnClickListener {
            intent.putExtra("category-id", "High-Top")
            startActivity(intent)
        }
        binding.icon3.setOnClickListener {
            intent.putExtra("category-id", "Athletic")
            startActivity(intent)
        }
        binding.icon4.setOnClickListener {
            intent.putExtra("category-id", "Slip-on")
            startActivity(intent)
        }
        binding.icon5.setOnClickListener {
            intent.putExtra("category-id", "Authentic")
            startActivity(intent)
        }
        binding.icon6.setOnClickListener {
            intent.putExtra("category-id", "Leather")
            startActivity(intent)
        }
        binding.icon7.setOnClickListener {
            intent.putExtra("category-id", "Canvas")
            startActivity(intent)
        }
        binding.icon8.setOnClickListener {
            intent.putExtra("category-id", "Synthetic")
            startActivity(intent)
        }

        val mainActivity = this
        binding.homeRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductAdapter(productList, mainActivity, productList)
        }

        //Item list initialization
        productController.onGetAllProducts(7)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = bindings.drawerLayout
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onClick(product: Product) {
        GlobalScope.launch(Dispatchers.Main) {
            productController.addViewedProduct(product)

            val intent = Intent(applicationContext, ProductDetail::class.java)
            intent.putExtra("item-id", product.id)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            val intent = Intent(this, AppSettings::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }
            R.id.nav_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_profile -> {
                val intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_viewed_products -> {
                val intent = Intent(this, ViewedProductActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_order -> {
                val intent = Intent(this, OrderListActivity::class.java)
                startActivity(intent)
            }
        }
        val drawerLayout: DrawerLayout = bindings.drawerLayout
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAllProducts() {
        //Load data
        GlobalScope.launch {
            try {
                val splash = installSplashScreen()
                splash.setKeepOnScreenCondition { true }

                productList.addAll(productController.getAllProducts())

                withContext(Dispatchers.Main) {
                    bindings.appBarNavigation.contentHome.homeRecyclerView.adapter?.notifyDataSetChanged()
                    splash.setKeepOnScreenCondition { false }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onShowAllProducts(products: ArrayList<Product>) {
        productList.addAll(products)
        bindings.appBarNavigation.contentHome.homeRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onShowProductDetail(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onShowProductsByCategory(products: ArrayList<Product>) {
        TODO("Not yet implemented")
    }
}