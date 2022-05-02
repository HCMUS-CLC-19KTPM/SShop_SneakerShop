package com.example.sshop_sneakershop.Account.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChoosePaymentMethodDialog :BottomSheetDialogFragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var accountController: AccountController
    private val auth = Firebase.auth
//    private var mListener: BottomSheetListener? = null
    private lateinit var paymentAdapter: PaymentDetailItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_payment_method,container,false)
        recyclerView = view.findViewById(R.id.choose_payment_recycler_view)
        accountController = AccountController()
        GlobalScope.launch(Dispatchers.Main) {
            val account = accountController.getUser(auth.currentUser!!.email!!)
            if (account != null) {
                val paymentList = account.payments
                if (paymentList !== null) {
                    paymentAdapter = PaymentDetailItemAdapter(paymentList)
                    recyclerView.adapter = paymentAdapter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
        return view
    }
//    interface BottomSheetListener {
//        fun onCreditCardClicked(values: ArrayList<String>)
//    }
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try {
//            mListener = context as BottomSheetListener
//        } catch (e: ClassCastException) {
//            throw ClassCastException(
//                "$context must implement BottomSheetListener"
//            )
//        }
//    }
}