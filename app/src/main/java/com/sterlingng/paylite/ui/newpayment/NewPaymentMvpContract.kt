package com.sterlingng.paylite.ui.newpayment

import com.sterlingng.paylite.ui.base.MvpPresenter

interface NewPaymentMvpContract<V : NewPaymentMvpView> : MvpPresenter<V> {
    fun loadCachedWallet()
}