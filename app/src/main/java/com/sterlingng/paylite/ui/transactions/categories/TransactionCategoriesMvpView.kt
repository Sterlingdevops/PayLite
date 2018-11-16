package com.sterlingng.paylite.ui.transactions.categories

import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.ui.base.MvpView

interface TransactionCategoriesMvpView : MvpView {
    fun updateCategories(it: ArrayList<Transaction>)
    fun initView(transactions: ArrayList<Transaction>)
    fun onGetUserTransactionsFailed()
    fun onGetUserTransactionsSuccessful(transactions: ArrayList<Transaction>)
    fun logout()
}
