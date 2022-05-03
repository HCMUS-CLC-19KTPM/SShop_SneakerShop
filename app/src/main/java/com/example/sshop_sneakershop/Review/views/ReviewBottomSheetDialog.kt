package com.example.sshop_sneakershop.Review.views

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Product.controllers.ProductController
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Review.models.Review
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.util.*

class ReviewBottomSheetDialog: BottomSheetDialogFragment() {
    private lateinit var accountController: AccountController
    private lateinit var productController: ProductController
    private var account: Account = Account()
    private var review: Review = Review()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_bottom_review, container, false)
        val productID = arguments?.getString("item-id")
        val productImage = arguments?.getString("item-image")
        accountController = AccountController()
        productController = ProductController()
        Log.i("review", "${productID} + ${productImage}")
        val image: ImageView = view.findViewById(R.id.review_product_image)
        Picasso.get().load(productImage).into(image)
        if (TextUtils.isEmpty(productImage)) {
            image.setImageResource(R.drawable.shoe)
        } else {
            Picasso.get().load(productImage).into(image)
        }
        val submitBtn: Button = view.findViewById(R.id.review_button_submit)
        val feedBackET : EditText = view.findViewById(R.id.review_edittext_feedback)
        val ratingBar : RatingBar = view.findViewById(R.id.review_rating_bar)
        submitBtn.setOnClickListener {
            val feedback = feedBackET.text.toString()
            val rating = ratingBar.rating
            CoroutineScope(Dispatchers.IO).launch {
                account = accountController.getUser()!!
                withContext(Dispatchers.Main){
                    review.userID = account.id
                    review.date = Date()
                    review.rate = rating.toDouble()
                    review.content = feedback
                    Log.i("review", "${account.id} + ${productID} + ${feedback} + ${rating}")
                    val result = productController.addReview(productID!!, account.id, review)
                    if(result) {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Success")
                            .setMessage("Your review has been submitted")
                            .setPositiveButton("OK") { _, _ ->
                                dismiss()
                            }
                            .show()
                    }else{
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Error")
                            .setMessage("Your review has not been submitted")
                            .setPositiveButton("OK") { _, _ ->
                                dismiss()
                            }
                            .show()
                    }
                }
            }
        }
        return view
    }
}