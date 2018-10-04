package com.sterlingng.paylite.ui.scheduled

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.ScheduledPayment
import com.sterlingng.paylite.ui.base.MvpView

interface ScheduledMvpView : MvpView {
    fun logout()
    fun onScheduledPaymentsFailed(response: Response)
    fun onScheduledPaymentsSuccessful(payments: ArrayList<ScheduledPayment>)
}