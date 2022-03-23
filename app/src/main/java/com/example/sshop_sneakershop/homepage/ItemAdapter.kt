package com.example.sshop_sneakershop.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.databinding.HomeProductBinding


class ItemAdapter(
    private val items: List<Item>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<CardViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder
    {
        val from = LayoutInflater.from(parent.context)
        val binding = HomeProductBinding.inflate(from, parent, false)
        return CardViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int)
    {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size
}