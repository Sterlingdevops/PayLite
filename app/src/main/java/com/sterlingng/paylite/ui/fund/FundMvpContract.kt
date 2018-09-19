package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.data.model.Card
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
    fun saveBank(bank: Bank)
    fun saveCard(card: Card)
}