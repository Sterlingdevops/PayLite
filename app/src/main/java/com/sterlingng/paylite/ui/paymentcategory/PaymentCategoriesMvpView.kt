package com.sterlingng.paylite.ui.paymentcategory

import com.sterlingng.paylite.data.model.PaymentCategory
import com.sterlingng.paylite.ui.base.MvpView

interface PaymentCategoriesMvpView : MvpView {
    fun logout()
    fun initView(paymentCategory: ArrayList<PaymentCategory>)
}
