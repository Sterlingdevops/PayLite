package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface FundMvpContract<V : FundMvpView> : MvpPresenter<V> {
    fun loadBanks()
    fun resolveCardNumber(bin: String)
    fun fundWallet(data: HashMap<String, Any>)
    fun resolveAccountNumber(accountNumber: String, bankCode: String)
}