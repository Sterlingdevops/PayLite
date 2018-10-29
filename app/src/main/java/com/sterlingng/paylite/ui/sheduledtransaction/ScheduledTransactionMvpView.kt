package com.sterlingng.paylite.ui.sheduledtransaction

import com.sterlingng.paylite.ui.base.MvpView

interface ScheduledTransactionMvpView : MvpView {
    fun onDeleteScheduledPaymentsFailed()
    fun onDeleteScheduledPaymentsSuccessful()
    fun logout()
}