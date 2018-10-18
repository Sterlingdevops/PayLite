package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.model.MenuItem
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface HomeMvpView : MvpView {
    fun logout()
    fun initView(currentUser: User?, mockMenuItems: ArrayList<MenuItem>)
    fun onGetWalletSuccessful(wallet: Wallet)
    fun onGetWalletFailed(response: Response)
}
