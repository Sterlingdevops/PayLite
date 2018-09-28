package com.sterlingng.paylite.ui.banktransfers.banktransferamount

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface BankTransferAmountMvpView : MvpView {
    fun initView(it: Wallet)
    fun logout()
    fun onBankTransferSuccessful()
    fun onBankTransferFailed(response: Response)
}