package com.sterlingng.paylite.ui.getcash

import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface GetCashMvpView : MvpView {
    fun initView(wallet: Wallet)
    fun onCashOutSuccessful()
    fun onCashOutFailed()
    fun logout()
}