package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface FundMvpContract<V : FundMvpView> : MvpPresenter<V> {
    fun loadBanks()
    fun loadCachedWallet()
    fun resolveCardNumber(bin: String)
    fun fundWalletWithBankAccount(data: HashMap<String, Any>)
    fun resolveAccountNumber(accountNumber: String, bankCode: String)
    fun fundWalletWithCard(data: HashMap<String, Any>)
    fun loadWallet()
}