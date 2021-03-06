package com.example.sshop_sneakershop.Account.views

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.models.Payment
import com.example.sshop_sneakershop.Cart.views.CartClickListener
import com.example.sshop_sneakershop.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PaymentDetailItemAdapter(
    private val payments:ArrayList<Payment>,
    private val clickListener: PaymentClickListener
): RecyclerView.Adapter<PaymentDetailItemAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View): RecyclerView.ViewHolder(listItemView){
        val nameTextView = listItemView.findViewById(R.id.PaymentDetail_Username_TV) as TextView
        val cardNumberTextView = listItemView.findViewById(R.id.PaymentDetail_CardNumber_TV) as TextView
        val dateCreateTextView = listItemView.findViewById(R.id.PaymentDetail_DateCreate_TV) as TextView
        val cardTypeImageView = listItemView.findViewById(R.id.PaymentDetail_CardType_IV) as ImageView
        val removeCardButton = listItemView.findViewById(R.id.PaymentDetail_button_remove_card) as Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val paymentView = inflater.inflate(R.layout.payment_detail_list_item, parent, false)
        return ViewHolder(paymentView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val payment = payments[position]
        holder.nameTextView.text = payment.name
        ("**** **** **** " + payment.number.substring(15,19)).also { holder.cardNumberTextView.text = it }
        val pattern = "MM-dd-yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(payment.since)
        holder.dateCreateTextView.text = date
        when (payment.type) {
            "visa" -> holder.cardTypeImageView.setImageResource(R.drawable.ic_visa)
            "master" -> holder.cardTypeImageView.setImageResource(R.drawable.ic_mastercard)
            "atm" -> holder.cardTypeImageView.setImageResource(R.drawable.ic_atm)
        }
        holder.removeCardButton.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Remove Card")
                .setMessage("Are you sure to remove this card ?")
                .setPositiveButton("Ok") { dialog, which ->
                    // Delete item
                    clickListener.onRemoveCard(position)
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }
    override fun getItemCount(): Int {
        return payments.size
    }

}