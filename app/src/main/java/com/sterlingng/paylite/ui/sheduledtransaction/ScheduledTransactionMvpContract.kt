package com.sterlingng.paylite.ui.sheduledtransaction

import com.sterlingng.paylite.data.model.ScheduledPayment
import com.sterlingng.paylite.ui.base.MvpPresenter

interface ScheduledTransactionMvpContract<V : ScheduledTransactionMvpView> : MvpPresenter<V> {
    fun cancelStandingOrder(scheduledPayment: ScheduledPayment)
}