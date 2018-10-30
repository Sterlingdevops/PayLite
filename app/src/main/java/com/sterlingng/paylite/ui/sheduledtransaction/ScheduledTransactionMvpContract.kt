package com.sterlingng.paylite.ui.sheduledtransaction

import com.sterlingng.paylite.data.model.ScheduledPayment
import com.sterlingng.paylite.ui.base.MvpPresenter

interface ScheduledTransactionMvpContract<V : ScheduledTransactionMvpView> : MvpPresenter<V> {
    fun cancelStandingOrder(scheduledPayment: ScheduledPayment)
    fun getUserRelativeTransactions(secondaryAcct: String)
    fun getTransactions(account: String, ref: String)
}