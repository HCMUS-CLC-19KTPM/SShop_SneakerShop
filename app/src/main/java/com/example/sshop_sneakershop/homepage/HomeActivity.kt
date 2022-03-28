package com.example.sshop_sneakershop.homepage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.sshop_sneakershop.Auth.View.AuthActivity
import com.example.sshop_sneakershop.Product.Product
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity(), ItemClickListener {
    // creating object of ViewPager
    private var mViewPager: ViewPager? = null
    private var images = intArrayOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
    private var mViewPagerAdapter: BannerAdapter? = null
    private var isLoading = true

    //Item binder
    private lateinit var binding: ActivityHomeBinding
    private lateinit var productList: List<Product>

    override fun onStart() {
        super.onStart()
        val auth = Firebase.auth
        if (auth.currentUser != null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition { isLoading }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        doApiCalls()

        //Banner - slider
        mViewPager = findViewById<View>(R.id.banner_view_pager) as ViewPager
        mViewPagerAdapter = BannerAdapter(this, images)
        mViewPager!!.adapter = mViewPagerAdapter

        //Item list initialization
        val myItem = Product("", 83.03, "Grand Court", R.drawable.shoe)
        productList = listOf(myItem, myItem, myItem, myItem)

        val mainActivity = this
        binding.homeRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ItemAdapter(productList, mainActivity)
        }

        //Set categories listener
        val intent = Intent(this, GroupItem::class.java)
        binding.icon1.setOnClickListener {
            intent.putExtra("categoryID", "Plimsoll")
            startActivity(intent)
            finish()
        }
        binding.icon2.setOnClickListener {
            intent.putExtra("categoryID", "High-Top")
            startActivity(intent)
            finish()
        }
        binding.icon3.setOnClickListener {
            intent.putExtra("categoryID", "Athletic")
            startActivity(intent)
            finish()
        }
        binding.icon4.setOnClickListener {
            intent.putExtra("categoryID", "Slip-on")
            startActivity(intent)
            finish()
        }
        binding.icon5.setOnClickListener {
            intent.putExtra("categoryID", "Authentic")
            startActivity(intent)
            finish()
        }
        binding.icon6.setOnClickListener {
            intent.putExtra("categoryID", "Leather")
            startActivity(intent)
            finish()
        }
        binding.icon7.setOnClickListener {
            intent.putExtra("categoryID", "Canvas")
            startActivity(intent)
            finish()
        }
        binding.icon8.setOnClickListener {
            intent.putExtra("categoryID", "Synthetic")
            startActivity(intent)
            finish()
        }
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ItemDetail::class.java)
        intent.putExtra("itemID", product.id)
        startActivity(intent)
        finish()
    }

    private fun doApiCalls() {
        //Load data
        isLoading = false
    }
}