package com.example.sshop_sneakershop.Product.views

import android.text.BoringLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.databinding.ProductListItemBinding
import com.example.sshop_sneakershop.databinding.RelatedProductListItemBinding


class ProductAdapter(
    private val products: ArrayList<Product>,
    private val clickListener: ItemClickListener,
    private val fullProductList: ArrayList<Product>
) : RecyclerView.Adapter<CardViewHolder>(), Filterable
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder
    {
        val from = LayoutInflater.from(parent.context)
        val binding = RelatedProductListItemBinding.inflate(from, parent, false)
        return CardViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int)
    {
        holder.bindItem(products[position])
    }

    override fun getItemCount(): Int = products.size
    override fun getFilter(): Filter {
        return productFilter
    }
    private val productFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: ArrayList<Product> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(fullProductList)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in fullProductList) {
                    if (item.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            Log.i("productAdapter", "${fullProductList.size}")
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            products.clear()
            products.addAll(results.values as ArrayList<Product>)
            notifyDataSetChanged()
        }
    }


}