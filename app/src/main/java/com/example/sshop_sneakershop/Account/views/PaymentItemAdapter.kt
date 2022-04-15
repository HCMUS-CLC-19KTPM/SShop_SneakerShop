package com.example.sshop_sneakershop.Account.views

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.models.Payment
import com.example.sshop_sneakershop.R

class PaymentItemAdapter(private val payments:ArrayList<Payment>?): RecyclerView.Adapter<PaymentItemAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View): RecyclerView.ViewHolder(listItemView){
        val nameTextView = listItemView.findViewById(R.id.Payment_Username_TV) as TextView
        val cardNumberTextView = listItemView.findViewById(R.id.Payment_CardNumber_TV) as TextView
        val dateCreateTextView = listItemView.findViewById(R.id.Payment_DateCreate_TV) as TextView
        val cardTypeImageView = listItemView.findViewById(R.id.Payment_CardType_IV) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val paymentView = inflater.inflate(R.layout.payment_list_item, parent, false)
        return ViewHolder(paymentView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val payment = payments!![position]
        holder.nameTextView.text = payment.name
        holder.cardNumberTextView.text = payment.number
        val pattern = "MM-dd-yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(payment.since)
        holder.dateCreateTextView.text = date
        when (payment.type) {
            "visa" -> holder.cardTypeImageView.setImageResource(R.drawable.ic_visa)
            "master" -> holder.cardTypeImageView.setImageResource(R.drawable.ic_mastercard)
            "atm" -> holder.cardTypeImageView.setImageResource(R.drawable.ic_atm)
        }

    }

    override fun getItemCount(): Int {
        return payments!!.size
    }
}