package com.sterlingng.paylite.ui.newpayment

import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface NewPaymentMvpView : MvpView {
    fun initView(wallet: Wallet?)
}