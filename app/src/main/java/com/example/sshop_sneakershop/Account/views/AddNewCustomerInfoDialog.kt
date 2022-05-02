package com.example.sshop_sneakershop.Account.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sshop_sneakershop.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddNewCustomerInfoDialog: BottomSheetDialogFragment() {
    private var mListener: BottomSheetListener? = null
    private var fullNameET: EditText?=null
    private var phoneET: EditText?=null
    private var addressET: EditText?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_checkout_add_new_information, container, false)
        val submitBtn: Button = view.findViewById(R.id.fragment_checkout_add_new_information_button_confirm)
        fullNameET = view.findViewById(R.id.fragment_checkout_add_new_information_edittext_name)
        phoneET = view.findViewById(R.id.fragment_checkout_add_new_information_edittext_phone)
        addressET = view.findViewById(R.id.fragment_checkout_add_new_information_edittext_address)

        var customerInfo: ArrayList<String> = arguments?.getStringArrayList("newCustomerInfo")!!
        fullNameET?.setText(customerInfo[0])
        phoneET?.setText(customerInfo[1])
        addressET?.setText(customerInfo[2])
        submitBtn.setOnClickListener {
            if(isValidInput()){
                val fullName = fullNameET!!.text.toString()
                val phone = phoneET!!.text.toString()
                val address = addressET!!.text.toString()
                val values: ArrayList<String> = arrayListOf(fullName, phone, address, "")
                mListener!!.onButtonClicked(values)
                dismiss()
            }
        }
        return view
    }
    fun isValidInput(): Boolean{
        if(fullNameET!!.text.toString().isEmpty()){
            fullNameET!!.error = "Please input customer name."
            return false
        }
        if(phoneET!!.text.toString().isEmpty()){
            phoneET!!.error = "Please input customer phone number."
            return false
        }
        if(addressET!!.text.toString().isEmpty()){
            addressET!!.error = "Please input customer address."
            return false
        }
        return true
    }

    interface BottomSheetListener {
        fun onButtonClicked(values: ArrayList<String>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as BottomSheetListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$context must implement BottomSheetListener"
            )
        }
    }
}