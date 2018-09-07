package com.sterlingng.paylite.ui.newpaymentamount

import com.sterlingng.paylite.ui.base.MvpPresenter
import java.util.*

interface NewPaymentAmountMvpContract<V : NewPaymentAmountMvpView> : MvpPresenter<V> {
    fun loadCachedWallet()
    fun sendMoney(data: HashMap<String, Any>)
}