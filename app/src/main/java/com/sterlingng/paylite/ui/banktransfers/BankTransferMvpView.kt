package com.sterlingng.paylite.ui.banktransfers

import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface BankTransferMvpView : MvpView {
    fun initView(wallet: Wallet)
}
