package com.sterlingng.paylite.ui.cashoutbank

import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

/**
 * Created by rtukpe on 21/03/2018.
 */

interface CashOutMvpView : MvpView {
    fun initView(wallet: Wallet?)
}
