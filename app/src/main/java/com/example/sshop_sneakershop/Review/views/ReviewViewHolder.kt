package com.example.sshop_sneakershop.Review.views

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Review.Review
import com.example.sshop_sneakershop.databinding.ReviewListItemBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat


class ReviewViewHolder(
    private val cardCellBinding: ReviewListItemBinding,
) : RecyclerView.ViewHolder(cardCellBinding.root) {
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    fun bindItem(review: Review) {

        val reviewer = review.user

        if (TextUtils.isEmpty(reviewer.avatar)) {
            cardCellBinding.icon1.setImageResource(R.drawable.ic_baseline_person_24)
        } else {
            Picasso.get().load(reviewer.avatar).into(cardCellBinding.icon1)
        }
        cardCellBinding.contentReview.text = review.content
        cardCellBinding.rating.rating = review.rate.toFloat()
        cardCellBinding.reviewer.text = reviewer.fullName
        val countReviews = reviewer.numOfReview
        cardCellBinding.reviewCount.text = "$countReviews Reviews"
        val formatter = SimpleDateFormat("dd/MM/yyyy - HH:mm a")
        val date = formatter.format(review.date)
        cardCellBinding.timeRating.text = date
        val upVote = review.rate.toFloat() >= 2.5
        if (upVote)
            cardCellBinding.imgUp.setColorFilter(R.color.primary)
        else cardCellBinding.imgDown.setColorFilter(R.color.primary)
    }
}