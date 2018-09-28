package com.sterlingng.paylite.ui.newpaymentamount

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface NewPaymentAmountMvpView : MvpView {
    fun initView(wallet: Wallet)
    fun onSendMoneySuccessful(wallet: Wallet)
    fun onSendMoneyFailed(response: Response)
    fun logout()
}