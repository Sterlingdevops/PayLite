package com.sterlingng.paylite.ui.transactions.insights

import com.sterlingng.paylite.data.model.PaymentCategory
import com.sterlingng.paylite.ui.base.MvpView

interface InsightsMvpView : MvpView {
    fun initView(mockPaymentCategories: ArrayList<PaymentCategory>)
}
