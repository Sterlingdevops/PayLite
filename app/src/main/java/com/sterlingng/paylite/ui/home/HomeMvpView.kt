package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface HomeMvpView : MvpView {
    fun onGetWalletFailed(it: Throwable)
    fun onGetWalletSuccessful(wallet: Wallet)
    fun initView(currentUser: User?)
}
