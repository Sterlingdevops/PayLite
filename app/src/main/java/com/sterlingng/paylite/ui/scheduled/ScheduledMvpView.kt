package com.sterlingng.paylite.ui.scheduled

import com.sterlingng.paylite.data.model.ScheduledPayment
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface ScheduledMvpView : MvpView {
    fun logout()
    fun onScheduledPaymentsFailed()
    fun onScheduledPaymentsSuccessful(payments: ArrayList<ScheduledPayment>)
    fun initView(wallet: Wallet, payments: ArrayList<ScheduledPayment>)
}