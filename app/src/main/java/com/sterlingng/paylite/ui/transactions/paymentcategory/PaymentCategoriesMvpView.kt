package com.sterlingng.paylite.ui.transactions.paymentcategory

import com.sterlingng.paylite.data.model.PaymentCategory
import com.sterlingng.paylite.ui.base.MvpView

interface PaymentCategoriesMvpView : MvpView {
    fun logout()
    fun initView(paymentCategory: ArrayList<PaymentCategory>)
    fun onUpdateTransactionCategorySuccessful()
    fun onUpdateTransactionCategoryFailed()
}
