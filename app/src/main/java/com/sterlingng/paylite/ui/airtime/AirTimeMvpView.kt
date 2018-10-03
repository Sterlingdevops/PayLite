package com.sterlingng.paylite.ui.airtime

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface AirTimeMvpView : MvpView {
    fun initView(wallet: Wallet?)
    fun onBuyAirtimeFailed(response: Response)
    fun onBuyAirtimeSuccessful()
    fun logout()
}
