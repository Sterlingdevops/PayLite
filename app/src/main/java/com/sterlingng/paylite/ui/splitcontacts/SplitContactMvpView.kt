package com.sterlingng.paylite.ui.splitcontacts

import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface SplitContactMvpView : MvpView {
    fun logout()
    fun onSplitPaymentFailed()
    fun initView(wallet: Wallet?)
    fun onSplitPaymentSuccessful()
}