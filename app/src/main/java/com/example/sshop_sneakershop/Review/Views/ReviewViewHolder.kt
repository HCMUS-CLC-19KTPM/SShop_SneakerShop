package com.example.sshop_sneakershop.Review.Views

import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Review.Review
import com.example.sshop_sneakershop.databinding.ProductReviewItemBinding


class ReviewViewHolder(
    private val cardCellBinding: ProductReviewItemBinding,
    ) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindItem(review: Review)
    {
        cardCellBinding.icon1.setImageResource(review.avatar)
        cardCellBinding.contentReview.text=review.content
        cardCellBinding.rating.rating= review.rate.toFloat()
        cardCellBinding.reviewer.text=review.reviewer
        val countReviews = review.count
        cardCellBinding.reviewCount.text="$countReviews Reviews"
        cardCellBinding.timeRating.text=review.date.toString()
        val upVote = review.upvote
        if (upVote)
            cardCellBinding.imgUp.setColorFilter(R.color.primary)
        else cardCellBinding.imgDown.setColorFilter(R.color.primary)
    }
}