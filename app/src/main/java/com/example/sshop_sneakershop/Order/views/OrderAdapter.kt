package com.example.sshop_sneakershop.Order.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.R

class OrderAdapter(private val orders:List<Order>): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View): RecyclerView.ViewHolder(listItemView){
        val customerNameTextView = listItemView.findViewById(R.id.order_list_textview_customer_name) as TextView
        val customerPhoneTextView = listItemView.findViewById(R.id.order_list_textview_customer_phone) as TextView
        val customerAddressTextView = listItemView.findViewById(R.id.order_list_textview_customer_address) as TextView
        val startDateTextView = listItemView.findViewById(R.id.order_list_start_date) as TextView
        val endDateTextView = listItemView.findViewById(R.id.order_list_textview_end_date) as TextView
        val totalCostTextView = listItemView.findViewById(R.id.order_list_textview_total_cost) as TextView
        val deliveryDescriptionTextView = listItemView.findViewById(R.id.order_list_textview_delivery_description) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val orderView = inflater.inflate(R.layout.order_list_item, parent, false)
        return ViewHolder(orderView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.customerNameTextView.text = order.name
        holder.customerPhoneTextView.text = order.phone
        holder.customerAddressTextView.text = order.address
        holder.startDateTextView.text = order.startDate.toString()
        holder.endDateTextView.text = order.endDate.toString()
        holder.totalCostTextView.text = order.totalCost.toString()
        holder.deliveryDescriptionTextView.text = order.id
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}