package com.example.sshop_sneakershop.Account.controllers

import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Account.models.AccountModel
import com.example.sshop_sneakershop.Account.views.IPaymentView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PaymentController(private val view: IPaymentView? = null) : IPaymentController {

    private val accountModel = AccountModel()

    override fun onGetPayments(id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val payments = accountModel.getPayments(id)
                if (payments != null) {
                    view?.onGetPaymentSuccess(payments)
                }
            } catch (e: Exception) {
                view?.onGetPaymentFail("Error: ${e.message}")
            }
        }
    }

    override fun onUpdatePayments(account: Account) {
        TODO("Not yet implemented")
    }
}