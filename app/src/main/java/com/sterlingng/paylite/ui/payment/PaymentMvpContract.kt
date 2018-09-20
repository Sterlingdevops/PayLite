package com.sterlingng.paylite.ui.payment

import com.sterlingng.paylite.data.model.PaymentMethod
import com.sterlingng.paylite.ui.base.MvpPresenter

interface PaymentMvpContract<V : PaymentMvpView> : MvpPresenter<V> {
    fun loadPaymentMethods()
    fun setCardDefault(paymentMethod: PaymentMethod)
    fun setBankDefault(paymentMethod: PaymentMethod)
}