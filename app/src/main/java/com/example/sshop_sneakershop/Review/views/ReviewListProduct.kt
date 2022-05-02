package com.example.sshop_sneakershop.Review.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Order.controllers.IOrderController
import com.example.sshop_sneakershop.Order.controllers.OrderController
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.Order.views.IOrderDetailActivity
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.CustomProductAdapter
import com.example.sshop_sneakershop.databinding.ActivityReviewListProductBinding

class ReviewListProduct : AppCompatActivity(), ItemClickListener, IOrderDetailActivity {
    private lateinit var orderController: IOrderController

    private val products: ArrayList<Product> = ArrayList()

    private lateinit var binding: ActivityReviewListProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        orderController = OrderController(this)

        super.onCreate(savedInstanceState)
        binding = ActivityReviewListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set up recycler view
        val reviewActivity = this
        binding.reviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = CustomProductAdapter(products, reviewActivity)
        }

        //Back button
        binding.reviewToolbar.setNavigationOnClickListener {
            finish()
        }

        orderController.onGetOrderById(intent.getStringExtra("order-id").toString())
    }
    override fun onClick(product: Product) {
        //Start review for single product
//        val intent = Intent(applicationContext, ProductDetail::class.java)
//        intent.putExtra("item-id", product.id)
//        startActivity(intent)
        val reviewBottomSheetDialog = ReviewBottomSheetDialog()
        val bundle = Bundle()
        bundle.putString("item-id", product.id)
        bundle.putString("item-image", product.image)
        reviewBottomSheetDialog.arguments = bundle
        reviewBottomSheetDialog.show(supportFragmentManager, "reviewBottomSheetDialog")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetOrderByIdSuccess(order: Order) {
        products.clear()
        products.addAll(order.cart)
        binding.reviewRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onGetOrderByIdFailed(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}