package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface FundMvpView : MvpView {
    fun onLoadBanksFailed(it: Throwable)
    fun onFundWalletFailed(it: Throwable)
    fun onLoadBanksSuccessful(it: Response)
    fun onFundWalletSuccessful(wallet: Wallet)
    fun onResolveCardNumberFailed(it: Throwable)
    fun onResolveCardNumberSuccessful(it: Response)
    fun onResolveAccountNumberFailed(it: Throwable)
    fun onResolveAccountNumberSuccessful(it: Response)
}
