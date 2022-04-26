package com.example.sshop_sneakershop.Order.views

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.OrderListItemBinding

class OrderAdapter(
    private val orders: ArrayList<Order>,
    private val clickListener: OrderClickListener,
    private val fullOrderList: ArrayList<Order>
) : RecyclerView.Adapter<OrderCardViewHolder>(), Filterable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderCardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = OrderListItemBinding.inflate(from, parent, false)
        return OrderCardViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: OrderCardViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun getFilter(): Filter {
        return orderFilter
    }
    private val orderFilter: Filter = object: Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<Order> = ArrayList()
            if (constraint == null || constraint.isEmpty()){
                filteredList.addAll(fullOrderList)
            }else{
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (item in fullOrderList){
                    if (item.name.toLowerCase().contains(filterPattern)){
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            orders.clear()
            orders.addAll(results?.values as ArrayList<Order>)
            notifyDataSetChanged()
        }
    }
}


//
//class OrderAdapter(private val orders:List<Order>): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
//    inner class ViewHolder(listItemView: View): RecyclerView.ViewHolder(listItemView){
//        val customerNameTextView = listItemView.findViewById(R.id.order_list_textview_customer_name) as TextView
//        val customerPhoneTextView = listItemView.findViewById(R.id.order_list_textview_customer_phone) as TextView
//        val customerAddressTextView = listItemView.findViewById(R.id.order_list_textview_customer_address) as TextView
//        val startDateTextView = listItemView.findViewById(R.id.order_list_start_date) as TextView
//        val endDateTextView = listItemView.findViewById(R.id.order_list_textview_end_date) as TextView
//        val totalCostTextView = listItemView.findViewById(R.id.order_list_textview_total_cost) as TextView
//        val deliveryDescriptionTextView = listItemView.findViewById(R.id.order_list_textview_delivery_description) as TextView
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val context = parent.context
//        val inflater = LayoutInflater.from(context)
//        val orderView = inflater.inflate(R.layout.order_list_item, parent, false)
//        return ViewHolder(orderView)
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val order = orders[position]
//        holder.customerNameTextView.text = order.name
//        holder.customerPhoneTextView.text = order.phone
//        holder.customerAddressTextView.text = order.address
//
//        val formatter = java.text.SimpleDateFormat("E, dd MMM yyyy HH:mm:ss")
//        holder.startDateTextView.text = formatter.format(order.startDate)
//        holder.endDateTextView.text = formatter.format(order.endDate)
//        holder.totalCostTextView.text = order.totalCost.toString()
//        holder.deliveryDescriptionTextView.text = order.id
//    }
//
//    override fun getItemCount(): Int {
//        return orders.size
//    }
//}