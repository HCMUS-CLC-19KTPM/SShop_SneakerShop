package com.example.sshop_sneakershop.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.databinding.HomeProductBinding
import com.example.sshop_sneakershop.databinding.ProductReviewItemBinding


class ReviewAdapter(
    private val reviews: List<Review>,
) : RecyclerView.Adapter<ReviewViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder
    {
        val from = LayoutInflater.from(parent.context)
        val binding = ProductReviewItemBinding.inflate(from, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int)
    {
        holder.bindItem(reviews[position])
    }

    override fun getItemCount(): Int = reviews.size
}