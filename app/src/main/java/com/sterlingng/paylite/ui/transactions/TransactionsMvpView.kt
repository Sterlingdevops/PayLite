package com.sterlingng.paylite.ui.transactions

import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface TransactionsMvpView : MvpView {
    fun initView(wallet: Wallet?)
}
