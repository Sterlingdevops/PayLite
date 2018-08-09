package com.sterlingng.paylite.ui.payment

import com.sterlingng.paylite.data.model.PaymentMethod
import com.sterlingng.paylite.ui.base.MvpView

interface PaymentMvpView : MvpView {
    fun updatePaymentMethods(it: ArrayList<PaymentMethod>)
}