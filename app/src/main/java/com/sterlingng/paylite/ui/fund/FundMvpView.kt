package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface FundMvpView : MvpView {
    fun initView(wallet: Wallet)
    fun onLoadBanksFailed(it: Throwable)
    fun onFundWalletFailed(it: Response)
    fun onFundWalletSuccessful()
    fun onResolveCardNumberFailed(response: Response?)
    fun onLoadBanksSuccessful(it: ArrayList<Bank>)
    fun onResolveCardNumberSuccessful(it: Response)
    fun onResolveAccountNumberFailed(it: Throwable)
    fun onResolveAccountNumberSuccessful(it: Response)
    fun onGetWalletSuccessful(wallet: Wallet?)
    fun onGetWalletFailed(response: Response?)
}
