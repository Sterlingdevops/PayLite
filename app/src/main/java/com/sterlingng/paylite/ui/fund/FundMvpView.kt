package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface FundMvpView : MvpView {
    fun initView(wallet: Wallet)
    fun onLoadBanksFailed()
    fun onFundWalletFailed()
    fun onFundWalletSuccessful()
    fun onResolveCardNumberFailed()
    fun onLoadBanksSuccessful(it: ArrayList<Bank>)
    fun onResolveCardNumberSuccessful(it: Response)
    fun onResolveAccountNumberFailed()
    fun onResolveAccountNumberSuccessful(it: Response)
    fun onGetWalletSuccessful(wallet: Wallet?)
    fun onGetWalletFailed()
    fun logout()
}
