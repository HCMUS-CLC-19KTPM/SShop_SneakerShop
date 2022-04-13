package com.example.sshop_sneakershop.Homepage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductAdapter
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityNavigationBinding

class Navigation : AppCompatActivity(), ItemClickListener {
    // creating object of ViewPager
    private var mViewPager: ViewPager? = null
    private var images = intArrayOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
    private var mViewPagerAdapter: BannerAdapter? = null
    private var productList: List<Product> = ArrayList()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bindings: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindings = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        setSupportActionBar(bindings.appBarNavigation.toolbar)

        val drawerLayout: DrawerLayout = bindings.drawerLayout
        val navView: NavigationView = bindings.navView
       /* val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        */
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("itemID", product.id)
        startActivity(intent)
    }
}