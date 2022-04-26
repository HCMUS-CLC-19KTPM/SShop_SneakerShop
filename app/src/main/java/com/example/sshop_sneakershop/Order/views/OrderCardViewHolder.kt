package com.example.sshop_sneakershop.Order.views

import android.annotation.SuppressLint
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Order.models.Order
import com.example.sshop_sneakershop.databinding.OrderListItemBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class OrderCardViewHolder(
    private val cardCellBinding: OrderListItemBinding,
    private val clickListener: OrderClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root) {

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    fun bind(order: Order) {
        cardCellBinding.orderListTextviewDeliveryDescription.text = "Standard Express - " + order.id
        val formatter = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss")
        val startDate = formatter.format(order.startDate)
        cardCellBinding.orderListStartDate.text = startDate
        val endDate = formatter.format(order.endDate)
        cardCellBinding.orderListTextviewEndDate.text = endDate
        cardCellBinding.orderListTextviewCustomerName.text = order.name
        cardCellBinding.orderListTextviewCustomerPhone.text = order.phone
        cardCellBinding.orderListTextviewCustomerAddress.text = order.address
        cardCellBinding.orderListTextviewTotalCost.text = "$${order.totalCost}"
        cardCellBinding.status.text = order.deliveryStatus
        cardCellBinding.card.setOnClickListener{
            clickListener.onClick(order)
        }
    }
}