package com.example.sshop_sneakershop.Homepage

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.sshop_sneakershop.Account.views.AccountActivity
import com.example.sshop_sneakershop.Cart.CartActivity
import com.example.sshop_sneakershop.Cart.models.Cart
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityNavigationBinding
import com.google.android.material.navigation.NavigationView

class Home : AppCompatActivity(), ItemClickListener,
    NavigationView.OnNavigationItemSelectedListener {
    // creating object of ViewPager
    private var mViewPager: ViewPager? = null
    private var images = intArrayOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
    private var mViewPagerAdapter: BannerAdapter? = null
    private var productList: List<Product> = ArrayList()

    private lateinit var bindings: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindings = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        setSupportActionBar(bindings.appBarNavigation.toolbar)
        supportActionBar?.title = "Home"

        val navView: NavigationView = bindings.navView
        navView.setNavigationItemSelectedListener(this)
        val toolbar: androidx.appcompat.widget.Toolbar = bindings.appBarNavigation.toolbar
        val drawerLayout: DrawerLayout = bindings.drawerLayout
        val openState: Int = 1
        val closeState: Int = 0
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

        //Newest Product
        val myItem = Product("", 83.03, "Grand Court", R.drawable.shoe)
        productList = listOf(myItem, myItem, myItem, myItem, myItem, myItem)

        val mainActivity = this
        binding.homeRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductAdapter(productList, mainActivity)
        }
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

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("itemID", product.id)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) true
        else super.onOptionsItemSelected(item)
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
        }
        val drawerLayout: DrawerLayout = bindings.drawerLayout
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }
}