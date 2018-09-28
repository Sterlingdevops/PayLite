package com.sterlingng.paylite.ui.banktransfers.banktransferamount

import com.sterlingng.paylite.ui.base.MvpPresenter

interface BankTransferAmountMvpContract<V : BankTransferAmountMvpView> : MvpPresenter<V> {
    fun loadCachedWallet()
    fun bankTransfer(data: HashMap<String, Any>)
}
