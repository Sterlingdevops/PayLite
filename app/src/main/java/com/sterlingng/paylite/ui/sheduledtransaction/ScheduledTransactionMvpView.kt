package com.sterlingng.paylite.ui.sheduledtransaction

import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.ui.base.MvpView

interface ScheduledTransactionMvpView : MvpView {
    fun onDeleteScheduledPaymentsFailed()
    fun onDeleteScheduledPaymentsSuccessful()
    fun logout()
    fun onGetUserRelativeTransactionsSuccessful()
    fun onGetUserRelativeTransactionsFailed()
    fun initView(transactions: ArrayList<Transaction>)
}
