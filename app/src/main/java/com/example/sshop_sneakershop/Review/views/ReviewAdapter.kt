package com.example.sshop_sneakershop.Review.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Review.Review
import com.example.sshop_sneakershop.databinding.ReviewListItemBinding


class ReviewAdapter(
    private val reviews: List<Review>,
) : RecyclerView.Adapter<ReviewViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder
    {
        val from = LayoutInflater.from(parent.context)
        val binding = ReviewListItemBinding.inflate(from, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int)
    {
        holder.bindItem(reviews[position])
    }

    override fun getItemCount(): Int = reviews.size
}