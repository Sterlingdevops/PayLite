package com.sterlingng.paylite.ui.newpaymentamount

import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface NewPaymentAmountMvpView : MvpView {
    fun initView(wallet: Wallet)
    fun onSendMoneySuccessful()
    fun onSendMoneyFailed()
    fun logout()
    fun onSchedulePaymentSuccessful()
    fun onSchedulePaymentFailed()
}