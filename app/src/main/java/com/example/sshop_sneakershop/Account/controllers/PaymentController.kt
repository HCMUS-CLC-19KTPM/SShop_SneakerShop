package com.example.sshop_sneakershop.Account.controllers

import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Account.models.AccountModel
import com.example.sshop_sneakershop.Account.views.IAccountEditActivity
import com.example.sshop_sneakershop.Account.views.IPaymentEditActivity
import com.example.sshop_sneakershop.Account.views.IPaymentView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PaymentController(
    private val editPaymentActivity: IPaymentEditActivity? = null
    ): IPaymentController {

    private val accountModel = AccountModel()

    /**
     * Update user's payments
     */
    suspend fun updatePayment(account: Account): Account? {
        return accountModel.updateUserPayment(account)
    }

    /**
     * Update user's payments using interface
     * @param account: Account
     */
    @OptIn(DelicateCoroutinesApi::class)
    override fun onUpdatePayment(account: Account) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val updatedAccount = updatePayment(account)
                if (updatedAccount != null) {
                    editPaymentActivity?.onUpdatePaymentSuccess(updatedAccount)
                } else {
                    editPaymentActivity?.onUpdatePaymentFail("Update user payment fail, please check your internet connection")
                }
            } catch (e: Exception) {
                editPaymentActivity?.onUpdatePaymentFail("Error: ${e.message}")
            }
        }
    }
}