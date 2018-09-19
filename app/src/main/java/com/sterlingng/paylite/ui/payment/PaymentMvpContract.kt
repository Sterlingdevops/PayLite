package com.sterlingng.paylite.ui.payment

import com.sterlingng.paylite.ui.base.MvpPresenter

interface PaymentMvpContract<V : PaymentMvpView> : MvpPresenter<V> {
    fun loadPaymentMethods()
}