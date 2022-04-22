package com.example.sshop_sneakershop.Account.controllers

import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Account.models.AccountModel
import com.example.sshop_sneakershop.Account.views.IAccountActivity
import com.example.sshop_sneakershop.Account.views.IAccountEditActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountController(
    private val accountActivity: IAccountActivity? = null,
    private val editActivity: IAccountEditActivity? = null
) : IAccountController {

    private val accountModel = AccountModel()

    /**
     * Get user info by email
     *
     * @param email
     * @return Account
     */
    suspend fun getUser(): Account? {
        return accountModel.getUser()
    }

    suspend fun getUser(email: String): Account? {
        return accountModel.getUser(email)
    }

    /**
     * Get user info by id using interface
     */
    @OptIn(DelicateCoroutinesApi::class)
    override fun onGetUser() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val account = accountModel.getUser()
                if (account != null) {
                    accountActivity?.onGetUserSuccess(account)
                } else {
                    accountActivity?.onGetUserFail("User not found")
                }
            } catch (e: Exception) {
                accountActivity?.onGetUserFail("Error: ${e.message}")
            }
        }
    }

    /**
     * Update user's information
     *
     * @param account: Account
     * @return Account
     */
    suspend fun updateUserInfo(account: Account): Account? {
        return accountModel.updateUserInfo(account)
    }

    /**
     * Update user's info using interface
     * @param account: Account
     */
    @OptIn(DelicateCoroutinesApi::class)
    override fun onUpdateUserInfo(account: Account) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val updatedAccount = updateUserInfo(account)

                if (updatedAccount != null) {
                    editActivity?.onUpdateUserInfoSuccess(updatedAccount)
                } else {
                    editActivity?.onUpdateUserInfoFailed("Update user info fail, please check your internet connection")
                }
            } catch (e: Exception) {
                editActivity?.onUpdateUserInfoFailed("Error: ${e.message}")
            }
        }
    }


    /**
     * Update user's payments
     */
    suspend fun updateUserPayment(account: Account): Account? {
        return accountModel.updateUserPayment(account)
    }

    /**
     * Update user's payments using interface
     * @param account: Account
     */
    @OptIn(DelicateCoroutinesApi::class)
    override fun onUpdateUserPayment(account: Account) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val updatedAccount = updateUserPayment(account)
                if (updatedAccount != null) {
                    accountActivity?.onUpdateUserPaymentSuccess(updatedAccount)
                } else {
                    accountActivity?.onUpdateUserPaymentFail("Update user payment fail, please check your internet connection")
                }
            } catch (e: Exception) {
                accountActivity?.onGetUserFail("Error: ${e.message}")
            }
        }
    }
}