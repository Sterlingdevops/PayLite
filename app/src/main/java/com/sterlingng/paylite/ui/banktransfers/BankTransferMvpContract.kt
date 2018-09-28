package com.sterlingng.paylite.ui.banktransfers

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface BankTransferMvpContract<V : BankTransferMvpView> : MvpPresenter<V> {
    fun loadWallet()
}
