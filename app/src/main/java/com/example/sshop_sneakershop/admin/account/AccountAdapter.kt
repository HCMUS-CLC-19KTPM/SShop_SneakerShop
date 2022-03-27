package com.example.sshop_sneakershop.checkout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.admin.account.Account

class AccountAdapter(private val accounts:List<Account>): RecyclerView.Adapter<AccountAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View): RecyclerView.ViewHolder(listItemView){
        val nameTextView = listItemView.findViewById(R.id.account_textview_username) as TextView
        val emailTextView = listItemView.findViewById(R.id.account_textview_email) as TextView
        val statusTextView = listItemView.findViewById(R.id.account_textview_status) as TextView
        val idTextView = listItemView.findViewById(R.id.account_textview_id) as TextView
        val avatarImageView = listItemView.findViewById(R.id.account_avatar) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val accountView = inflater.inflate(R.layout.admin_account_list_item, parent, false)
        return ViewHolder(accountView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accounts[position]
        holder.nameTextView.text = account.fullname
        holder.emailTextView.text = account.email
        if (account.status) {
            holder.statusTextView.text = "Active"
        } else {
            holder.statusTextView.text = "Banned"
        }
        holder.idTextView.text = account.id
        holder.avatarImageView.setImageResource(R.drawable.account_avatar)

    }

    override fun getItemCount(): Int {
        return accounts.size
    }
}