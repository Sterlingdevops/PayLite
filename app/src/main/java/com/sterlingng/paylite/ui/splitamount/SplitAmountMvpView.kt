package com.sterlingng.paylite.ui.splitamount

import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface SplitAmountMvpView : MvpView {
    fun initView(wallet: Wallet?)
}